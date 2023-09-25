package com.anynote.common.security.aspect;

import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.exception.auth.InnerAuthException;
import com.anynote.core.utils.ServletUtils;
import com.anynote.core.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 内部服务调用验证
 *
 * @author 称霸幼儿园
 */
@Aspect
@Component
public class InnerAuthAspect implements Ordered {
    @Around("@annotation(innerAuth)")
    public Object innerAround(ProceedingJoinPoint point, InnerAuth innerAuth) throws Throwable {
        String source = ServletUtils.getRequest().getHeader(SecurityConstants.FROM_SOURCE);

        if (!StringUtils.equals(SecurityConstants.INNER, source)) {
            throw new InnerAuthException("没有内部访问权限，不允许访问");
        }

        String accessToken = ServletUtils.getRequest().getHeader(SecurityConstants.ACCESS_TOKEN);
        // 用户信息验证（不确定是否需要）
        if (innerAuth.isUser() && StringUtils.isEmpty(accessToken)) {
            throw new InnerAuthException("没有设置用户信息，不允许访问");
        }
        return point.proceed();
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
