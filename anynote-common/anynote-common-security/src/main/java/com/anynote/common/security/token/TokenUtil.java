package com.anynote.common.security.token;

import com.alibaba.fastjson2.JSON;
import com.anynote.common.redis.enums.CachePrefixEnum;
import com.anynote.common.redis.service.RedisService;
import com.anynote.common.security.enums.SecurityEnum;
import com.anynote.common.security.properties.JWTTokenProperties;
import com.anynote.common.security.utils.SecurityUtils;
import com.anynote.core.exception.auth.TokenException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.bo.Token;
import com.anynote.system.api.model.po.SysUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Token 工具
 * @author 称霸幼儿园
 */
@Component
public class TokenUtil {

    @Autowired
    private JWTTokenProperties jwtTokenProperties;

    @Autowired
    private RedisService redisService;

    /**
     * 生成Token
     * @param loginUser 用户信息
     * @param longTerm 是否是长期token
     * @return 生成的Token
     */
    public Token createToken(LoginUser loginUser) {
        Token token = new Token();
        // 创建Token
        String accessToken = createToken(loginUser, jwtTokenProperties.getTokenExpireTime());

        Long expireTime = loginUser.isLongTerm() ? 15 * 24 * 60 : jwtTokenProperties.getTokenExpireTime() * 60 * 2;
        String refreshToken = createToken(loginUser, expireTime);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        loginUser.setToken(token);
        redisService.setCacheObject(CachePrefixEnum.ACCESS_TOKEN.getPrefix(loginUser.getUsername()) + accessToken,
                loginUser, jwtTokenProperties.getTokenExpireTime() * 60, TimeUnit.SECONDS);
        redisService.setCacheObject(CachePrefixEnum.REFRESH_TOKEN.getPrefix(loginUser.getUsername()),
                loginUser, expireTime, TimeUnit.SECONDS);
        return token;
    }

    public LoginUser getLoginUser() {
        String accessToken = SecurityUtils.getAccessToken();
        LoginUser loginUser = getLoginUser(accessToken);
        checkLoginUser(loginUser);
        return loginUser;
    }

    private void checkLoginUser(LoginUser loginUser) {
        if (loginUser == null) {
            throw new TokenException(ResCode.AUTH_ERROR);
        }
        if (loginUser.getUserId() == null) {
            throw new TokenException(ResCode.AUTH_ERROR);
        }
        SysUser sysUser = loginUser.getSysUser();
        if (sysUser == null) {
            throw new TokenException(ResCode.AUTH_ERROR);
        }
        if (sysUser.getId() == null) {
            throw new TokenException(ResCode.AUTH_ERROR);
        }
    }

    public LoginUser getLoginUser(String accessToken) {
        if (StringUtils.isNotNull(accessToken)) {
            LoginUser loginUser = this.authToken(accessToken);
            if (StringUtils.isNotNull(loginUser)) {
                return redisService.getCacheObject(CachePrefixEnum.ACCESS_TOKEN.getPrefix(loginUser.getUsername()) + accessToken);
            }

        }
        return null;
    }


    /**
     * 刷新Token
     * @param oldRefreshToken
     * @return
     */
    public Token refreshToken(String oldRefreshToken) {
        LoginUser tokenLoginUser = authToken(oldRefreshToken);
        if (tokenLoginUser == null) {
            throw new TokenException("refreshToken过期", ResCode.TOKEN_EXPIRED);
        }

        LoginUser loginUser =
                redisService.getCacheObject(CachePrefixEnum.REFRESH_TOKEN.getPrefix(tokenLoginUser.getUsername()) + oldRefreshToken);
        if (loginUser == null) {
            throw new TokenException("refreshToken过期", ResCode.TOKEN_EXPIRED);
        }

        Token token = createToken(loginUser);
        redisService.deleteObject(CachePrefixEnum.REFRESH_TOKEN.getPrefix(tokenLoginUser.getUsername()) + oldRefreshToken);
        return token;
    }


    public LoginUser authToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Map<String, Claim> claimsMap;
        LoginUser loginUser = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtTokenProperties.getSecret())).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            claimsMap = decodedJWT.getClaims();
            loginUser = JSON.parseObject(claimsMap.get(SecurityEnum.USER_CONTEXT.getValue()).asString(), LoginUser.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return loginUser;
    }

    private String createToken(LoginUser loginUser, Long expirationTime) {
        Date expireDate = new Date(System.currentTimeMillis() + expirationTime * 60 * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create()
                .withHeader(map)
                .withClaim(SecurityEnum.USER_CONTEXT.getValue(), JSON.toJSONString(loginUser))
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(jwtTokenProperties.getSecret()));
    }
}
