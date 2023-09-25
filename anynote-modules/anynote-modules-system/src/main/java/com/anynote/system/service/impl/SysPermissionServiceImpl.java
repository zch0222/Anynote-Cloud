package com.anynote.system.service.impl;

import com.anynote.system.api.model.po.SysUser;
import com.anynote.system.service.SysPermissionService;
import com.anynote.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 用户权限服务
 * @author 称霸幼儿园
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 用户角色的权限信息
     * @param sysUser 用户信息
     * @return
     */
    @Override
    public Set<String> getRolePermission(SysUser sysUser) {

    }
}
