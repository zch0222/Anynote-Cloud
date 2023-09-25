package com.anynote.system.service;

import com.anynote.system.api.model.po.SysUser;

import java.util.Set;

/**
 * 权限信息
 *
 * @author 称霸幼儿园
 */
public interface SysPermissionService {

    /**
     * 根据角色获取权限
     * @param sysUser 用户信息
     * @return 权限信息
     */
    public Set<String> getRolePermission(SysUser sysUser);
}
