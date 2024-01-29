package com.anynote.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.anynote.common.redis.service.RedisService;
import com.anynote.common.security.token.TokenUtil;
import com.anynote.common.security.utils.SecurityUtils;
import com.anynote.core.constant.HttpStatus;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.exception.auth.TokenException;
import com.anynote.core.utils.ServletUtils;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.gateway.properties.SecurityIgnoreProperties;
import com.anynote.gateway.properties.SecurityManageProperties;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.po.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 认证过滤器
 * @author 称霸幼儿园
 */
@Component
@Slf4j
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SecurityIgnoreProperties securityIgnoreProperties;

    @Resource
    private SecurityManageProperties securityManageProperties;


    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();

        if (StringUtils.matches(url, securityIgnoreProperties.getWhites())) {
            return chain.filter(exchange);
        }

        String accessToken = getAccessToken(exchange.getRequest());

        LoginUser loginUser = null;

        try {
            loginUser = authAccessToken(exchange, accessToken, url);
        } catch (TokenException e) {
            log.error(e.getErrorMessage(), e);
            return unauthorizedResponse(exchange, e.getErrorCode());
        }

        addHeader(mutate, SecurityConstants.ACCESS_TOKEN, accessToken);
        addHeader(mutate, SecurityConstants.DETAILS_USER_ID, loginUser.getUserId());

        // 内部请求来源信息消除
        removeHeader(mutate, SecurityConstants.FROM_SOURCE);

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private void removeHeader(ServerHttpRequest.Builder mutate, String name)
    {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }


    private String getAccessToken(ServerHttpRequest request) {
        return request.getHeaders().getFirst(SecurityConstants.ACCESS_TOKEN);
    }

    //    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        String path = exchange.getRequest().getPath().value();
//        if (securityIgnoreProperties.getWhites().stream().anyMatch(path::startsWith)) {
//            return chain.filter(exchange);
//        }
//
//        String accessToken = SecurityUtils.getAccessToken();
//
//        authAccessToken(accessToken);
//
//        return chain.filter(exchange);
//    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value)
    {
        if (value == null)
        {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    private LoginUser authAccessToken(ServerWebExchange exchange, String accessToken, String url) {
        if (StringUtils.isNull(accessToken)) {
            throw new TokenException(ResCode.ACCESS_TOKEN_NOT_FOUND);
        }

        LoginUser loginUser = tokenUtil.getLoginUser(accessToken);

        if (StringUtils.isNull(loginUser)) {
            throw new TokenException(ResCode.ACCESS_TOKEN_NOT_FOUND);
        }

        if (StringUtils.matches(url, securityManageProperties.getUrls()) && !SysUser.isAdminX(loginUser.getSysUser().getRole())) {
            throw new TokenException(ResCode.UNAUTHORIZED_ERROR);
        }
        return loginUser;

    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, ResCode resCode) {
        log.error("[鉴权异常]请求地址：{}", exchange.getRequest().getPath());
        ServerHttpResponse response = exchange.getResponse();
        response.setRawStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(ResData.error(resCode)).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
