package com.anynote.common.security.aspect;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.condition.SpringMvcCondition;
import com.anynote.core.condition.SpringWebfluxCondition;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.constant.SpringWebfluxContextConstants;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.model.bo.LogBO;
import com.anynote.system.api.model.bo.LoginUser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Arrays;

@Aspect
@Component
@Conditional(SpringWebfluxCondition.class)
@Slf4j(topic = "RequestLogger")
public class LogAspectWebFlux {

    @Resource
    private TokenUtil tokenUtil;

    private final ReactiveAdapterRegistry adapterRegistry = ReactiveAdapterRegistry.getSharedInstance();

    @Pointcut("execution(* com.anynote..controller..*.*(..))")
    public void controllerPointcut() {}


    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Mono<LogBO> logBOMono = buildLogBO(joinPoint.getArgs()[0]);

        Object result = joinPoint.proceed();

        return handleReactiveResult(result, logBOMono, startTime);
    }

    private Mono<LogBO> buildLogBO(Object args) {
        LogBO logBO = new LogBO();

        return Mono.deferContextual(ctx -> {
//            log.info("GET LOGIN USER");
            LoginUser loginUser = ctx.get("LOGIN_USER");
            logBO.setIp(((InetSocketAddress)ctx.get(SecurityConstants.IP_ADDRESS)).toString());
            logBO.setUrl(ctx.get(SecurityConstants.URI).toString());
            logBO.setMethod(ctx.get(SecurityConstants.METHOD).toString());
            try {
                logBO.setRequestArgs(new Gson().toJson(args));
            } catch (Throwable ex) {
                log.error("序列化请求参数失败", ex);
            }
            logBO.setUserId(loginUser.getUserId());
            logBO.setNickName(loginUser.getSysUser().getNickname());
            logBO.setUserName(loginUser.getUsername());
            return Mono.just(logBO);
        });
    }

    private Object handleReactiveResult(Object result, Mono<LogBO> logBOMono, long startTime) {
        if (result instanceof Mono) {
            return logBOMono.flatMap(logBO -> ((Mono<?>) result)
                    .doOnNext(response -> processSuccess(response, logBO, startTime))
                    .doOnError(throwable -> processError(throwable, logBO, startTime)));
        } else if (result instanceof Flux) {
            return logBOMono.flux()
                    .flatMap(logBO -> ((Flux<?>) result)
                            .doOnNext(response -> processSuccess(response, logBO, startTime))
                            .doOnError(throwable -> processError(throwable, logBO, startTime)));
        }
        return result;
    }

    private void processSuccess(Object response, LogBO logBO, long startTime) {
        logBO.setSuccess(1);
        logBO.setTimeConsuming(System.currentTimeMillis() - startTime);
        try {
            logBO.setResponse(new Gson().toJson(response));
        } catch (Throwable e) {
            log.error("序列化响应失败", e);
        }
        printLog(logBO);
    }

    private void processError(Throwable e, LogBO logBO, long startTime) {
        logBO.setSuccess(0);
        logBO.setTimeConsuming(System.currentTimeMillis() - startTime);
        if (e instanceof BusinessException) {
            logBO.setErrorMsg(((BusinessException) e).getErrorMessage());
        } else {
            logBO.setErrorMsg(e.getMessage());
        }
        printLog(logBO);
    }

    private void printLog(LogBO logBO) {
        try {
            log.info(new Gson().toJson(logBO));
        } catch (Throwable e) {
            log.error("日志打印失败", e);
        }
    }
}
