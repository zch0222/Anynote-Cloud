package com.anynote.common.security.aspect;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.condition.SpringMvcCondition;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.model.bo.LogBO;
import com.anynote.system.api.model.bo.LoginUser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Conditional(SpringMvcCondition.class)
@Slf4j(topic = "RequestLogger")
public class LogAspect {

    @Resource
    private TokenUtil tokenUtil;


    // 定义切点：拦截所有Controller包下的方法
    @Pointcut("execution(* com.anynote..controller..*.*(..))")
    public void controllerPointcut() {}

    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        LogBO logBO = new LogBO();
        long startTime = System.currentTimeMillis();
        try {
            if (request != null) {
                LoginUser loginUser = null;
                try {
                    loginUser = tokenUtil.getLoginUser();
                } catch (Exception e) {
                    log.error("Log获取用户信息失败", e);
                }
                logBO.setIp(request.getRemoteAddr());
                logBO.setUrl(request.getRequestURI());
                logBO.setMethod(request.getMethod());
                logBO.setRequestArgs(Arrays.toString(joinPoint.getArgs()));
                if (loginUser != null) {
                    logBO.setUserId(loginUser.getUserId());
                    logBO.setUserName(loginUser.getUsername());
                    logBO.setNickName(loginUser.getSysUser().getNickname());
                }
            }
        } catch (Throwable e) {
            log.error("Log Request Error", e);
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logBO.setSuccess(0);
            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;
                logBO.setErrorMsg(businessException.getErrorMessage());
            }
            else {
                try {
                    logBO.setErrorMsg(new Gson().toJson(e));
                } catch (Throwable ex) {
                    log.error("Get Error Message Error", ex);
                }
            }
            logBO.setTimeConsuming(System.currentTimeMillis() - startTime);
            printLog(logBO);
            throw e;
        }
        try {
            logBO.setSuccess(1);
            logBO.setTimeConsuming(System.currentTimeMillis() - startTime);
            logBO.setResponse(new Gson().toJson(result));
            printLog(logBO);
        } catch (Throwable e) {
            log.error("Log Response Error", e);
        }
        return result;
    }

    private void printLog(LogBO logBO) {
        try {
            log.info(new Gson().toJson(logBO));
        } catch (Throwable e) {
            log.error("Print Log Error", e);
        }
    }

}
