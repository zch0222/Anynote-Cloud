package com.anynote.common.datascope.aspect;

import com.anynote.common.datascope.annotation.RolePermissions;
import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.enums.Role;
import com.anynote.core.exception.auth.AuthException;
import com.anynote.system.api.model.bo.LoginUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Order(0)
@Aspect
@Component
public class RolePermissionsAspect extends BasePermissionsAspect {

    @Resource
    private TokenUtil tokenUtil;

    @Before("@annotation(rolePermissions)")
    public void doBefore(JoinPoint joinPoint, RolePermissions rolePermissions) {
        LoginUser loginUser = tokenUtil.getLoginUser();

        Role[] roles = rolePermissions.value();
        for (Role role : roles) {
            if (role.getValue().equals(loginUser.getRole())) {
                return;
            }
        }
        throw new AuthException("没有权限");
    }


}
