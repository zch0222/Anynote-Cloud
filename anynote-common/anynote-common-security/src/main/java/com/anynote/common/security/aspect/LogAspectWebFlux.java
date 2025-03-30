package com.anynote.common.security.aspect;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.condition.SpringMvcCondition;
import com.anynote.core.condition.SpringWebfluxCondition;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//@Aspect
@Component
@Conditional(SpringWebfluxCondition.class)
@Slf4j
public class LogAspectWebFlux {

//    @Resource
//    private TokenUtil tokenUtil;
//
//
//    // 定义切点：拦截所有Controller包下的方法
//    @Pointcut("execution(* com.anynote..controller..*.*(..))")
//    public void controllerPointcut() {}
//
//
//    @Around("controllerPointcut()")
//    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//
//    }
}
