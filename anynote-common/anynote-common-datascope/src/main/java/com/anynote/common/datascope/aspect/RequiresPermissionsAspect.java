package com.anynote.common.datascope.aspect;

import com.anynote.common.datascope.annotation.RequiresPermissions;
import com.anynote.common.datascope.constants.PermissionConstants;
import com.anynote.common.datascope.model.bo.PermissionAuthBO;
import com.anynote.common.datascope.service.PermissionService;
import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.condition.SpringMvcCondition;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.exception.auth.AuthException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.BaseEntity;
import com.anynote.core.web.model.bo.QueryParam;
import com.anynote.system.api.model.bo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

@Order(1)
@Aspect
@Component
@Slf4j
@Conditional(SpringMvcCondition.class)
public class RequiresPermissionsAspect {

    @Resource
    private TokenUtil  tokenUtil;

    @Resource
    private PermissionService permissionService;


    private Object getQueryParam(JoinPoint joinPoint, String queryParamName) {
        // 获取方法的参数名
        String[] argNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        // 获取方法的参数值
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < argNames.length; i++) {
            if (queryParamName.equals(argNames[i])) {
                // 找到指定名称的参数
                return args[i];
            }
        }
        log.error(StringUtils.format("查询参数名称：{}不存在", queryParamName));
        throw new BusinessException("未知异常，请联系管理员");
    }

    private void setParams(Object queryParam, String key, Object value) {
        if (!(queryParam instanceof BaseEntity)) {
            return;
        }
        BaseEntity baseEntity = (BaseEntity) queryParam;
        if (StringUtils.isNull(baseEntity.getParams())) {
            baseEntity.setParams(new HashMap<>());
        }
        baseEntity.getParams().put(key, value);
    }

    @Before("@annotation(requiresPermissions)")
    public void doBefore(JoinPoint joinPoint, RequiresPermissions requiresPermissions) {
        Object queryParam = this.getQueryParam(joinPoint, requiresPermissions.queryParamName());
        String getterMethodName = "get" + Character.toUpperCase(requiresPermissions.paramIdName().charAt(0)) +
                requiresPermissions.paramIdName().substring(1);
        Long entityId = null;
        LoginUser loginUser = tokenUtil.getLoginUser();
        try {
            entityId = (Long) queryParam.getClass().getMethod(getterMethodName).invoke(queryParam);
        } catch (Exception e) {
            String errorMessage = StringUtils.format("获取id方法: {}不存在", getterMethodName);
            log.error(errorMessage, e);
            throw new BusinessException("未知异常，请联系管理员");
        }
        PermissionAuthBO permissionAuthBO = permissionService.auth(requiresPermissions.value(), entityId, loginUser.getUserId());
        setParams(queryParam ,PermissionConstants.PERMISSION_CONTEXT_KEY, permissionAuthBO.getPermission());
        if (!permissionAuthBO.isAuthenticationSuccess()) {
            throw new AuthException();
        }
    }
}
