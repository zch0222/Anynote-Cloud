package com.anynote.common.datascope.aspect;

import com.anynote.common.datascope.annotation.DataScopeInterceptor;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.BaseEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 称霸幼儿园
 */
@Order(1)
@Component
@Aspect
public class DataScopeInterceptorAspect {


    @Before("@annotation(interceptor)")
    public void doBefore(JoinPoint joinPoint, DataScopeInterceptor interceptor) {
        clearDataScope(joinPoint);
        addDataScope(joinPoint);

    }

    private void clearDataScope(final JoinPoint joinPoint) {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) params;
            Map<String, Object> map = new HashMap<>();
            map.put(DataScopeAspect.DATA_SCOPE, "");
            baseEntity.setParams(map);
        }
    }

    private void addDataScope(final JoinPoint joinPoint) {
        if (StringUtils.isNull(RequestContextHolder.getRequestAttributes()
                .getAttribute(DataScopeAspect.DATA_SCOPE, RequestAttributes.SCOPE_REQUEST))) {
            return;
        }
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(DataScopeAspect.DATA_SCOPE, RequestContextHolder.getRequestAttributes()
                    .getAttribute(DataScopeAspect.DATA_SCOPE, RequestAttributes.SCOPE_REQUEST));
        }
    }
}
