package com.anynote.common.datascope.aspect;

import com.anynote.common.datascope.annotation.DataScope;
import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.po.SysRole;
import com.anynote.system.api.model.po.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 数据过滤处理
 *
 * @author 称霸幼儿园
 */
@Order(1)
@Aspect
@Component
public class DataScopeAspect {

    @Autowired
    private TokenUtil tokenUtil;

    /**
     * 全部数据权限
     */
    public static final int DATA_SCOPE_ALL = 1;

    /**
     * 自定数据权限
     */
    public static final int DATA_SCOPE_CUSTOM = 2;

    /**
     * 部门数据权限
     */
    public static final int DATA_SCOPE_DEPT = 3;

    /**
     * 部门及以下数据权限
     */
    public static final int DATA_SCOPE_DEPT_AND_CHILD = 4;

    /**
     * 仅自己数据
     */
    public static final int DATA_SCOPE_SELF = 5;

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    @Before("@annotation(dataScope)")
    public void doBefore(JoinPoint point, DataScope dataScope) {
        handleDataScope(point, dataScope);
    }

    private void handleDataScope(final JoinPoint joinPoint, DataScope dataScope) {

        LoginUser loginUser = tokenUtil.getLoginUser();
        if (StringUtils.isNotNull(loginUser)) {
            SysUser sysUser = loginUser.getSysUser();
            // 超级管理员可以看到所有的数据
            if (StringUtils.isNotNull(sysUser) && !SysUser.isAdminX(sysUser.getRole()
            )) {
                dataScopeFilter(joinPoint, sysUser, dataScope.organizationAlias(), dataScope.userAlias());
            }
        }
    }

    private void dataScopeFilter(JoinPoint joinPoint, SysUser sysUser, String organizationAlias, String userAlias) {
        StringBuilder sqlString = new StringBuilder();

        SysRole sysRole = sysUser.getRole();

        int dataScope = sysRole.getDataScope();
        if (DATA_SCOPE_ALL == dataScope) {

        }

        if (DATA_SCOPE_SELF == dataScope) {
            if (userAlias.equals("sys_user")) {
                sqlString.append(StringUtils.format(" OR sys_user.id = {}", sysUser.getId()));
            }
            else {
                sqlString.append(StringUtils.format(" OR {}.user_id = {}", userAlias, sysUser.getId()));
            }
        }

        if (StringUtils.isNotBlank(sqlString.toString())) {
            RequestContextHolder.getRequestAttributes().setAttribute(DATA_SCOPE, "AND (" + sqlString.substring(4) + ")",
                    RequestAttributes.SCOPE_REQUEST);
        }

    }


}
