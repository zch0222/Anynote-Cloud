package com.anynote.common.security.token;

import com.alibaba.fastjson2.JSON;
import com.anynote.common.redis.service.RedisService;
import com.anynote.common.security.enums.SecurityEnum;
import com.anynote.common.security.properties.JWTTokenProperties;
import com.anynote.system.api.model.bo.LoginUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * @return
     */
    public Token createToken(LoginUser loginUser, boolean longTerm) {
        return null;
    }


    private String buildJWTToken(LoginUser loginUser, Long expirationTime) {
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
