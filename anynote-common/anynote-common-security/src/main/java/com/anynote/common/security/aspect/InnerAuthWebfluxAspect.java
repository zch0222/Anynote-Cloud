package com.anynote.common.security.aspect;

import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.common.security.condition.SpringMvcCondition;
import com.anynote.core.condition.SpringWebfluxCondition;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.exception.auth.InnerAuthException;
import com.anynote.core.utils.ServletUtils;
import com.anynote.core.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 内部服务调用验证（Spring Webflux）
 * @author 称霸幼儿园
 */
@Aspect
@Component
@Conditional(SpringWebfluxCondition.class)
@Order(0)
public class InnerAuthWebfluxAspect {


    @Before("@annotation(innerAuth)")
    public void doBefore(JoinPoint joinPoint, InnerAuth innerAuth) throws Throwable {
        String source = (String) joinPoint.getArgs()[0];

        if (!StringUtils.equals(SecurityConstants.INNER, source)) {
            throw new InnerAuthException("没有内部访问权限，不允许访问");
        }

//        String accessToken = ServletUtils.getRequest().getHeader(SecurityConstants.ACCESS_TOKEN);
//        // 用户信息验证（不确定是否需要）
//        if (innerAuth.isUser() && StringUtils.isEmpty(accessToken)) {
//            throw new InnerAuthException("没有设置用户信息，不允许访问");
//        }
    }
}
