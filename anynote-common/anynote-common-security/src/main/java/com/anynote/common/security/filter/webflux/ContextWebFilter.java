package com.anynote.common.security.filter.webflux;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.condition.SpringWebfluxCondition;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.constant.SpringWebfluxContextConstants;
import com.anynote.core.utils.StringUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * SpringWebflux 获取header
 */
@Slf4j
@Component
@Conditional(SpringWebfluxCondition.class)
public class ContextWebFilter implements WebFilter {

    @Resource
    private TokenUtil tokenUtil;


    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        String accessToken = exchange.getRequest().getHeaders().getFirst(SecurityConstants.ACCESS_TOKEN);
        HttpMethod method = exchange.getRequest().getMethod();
        URI uri = exchange.getRequest().getURI();
        InetSocketAddress ipAddress = exchange.getRequest().getRemoteAddress();
        if (StringUtils.isNotNull(accessToken)) {
            return chain.filter(exchange)
                    .contextWrite(ctx -> ctx.put(SecurityConstants.ACCESS_TOKEN, accessToken))
                    .contextWrite(ctx -> ctx.put(SecurityConstants.METHOD, method))
                    .contextWrite(ctx -> ctx.put(SecurityConstants.URI, uri))
                    .contextWrite(ctx -> ctx.put(SecurityConstants.IP_ADDRESS, ipAddress))
                    .contextWrite(ctx -> ctx.put(SpringWebfluxContextConstants.LOGIN_USER, tokenUtil.getLoginUser(accessToken)));
        }
        return chain.filter(exchange);
    }
}
