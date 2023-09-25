package com.anynote.system.service;

import com.anynote.system.api.model.po.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;


/**
 * 角色服务
 *
 * @author 称霸幼儿园
 */
public interface SysRoleService extends IService<SysRole> {
    public Set<String> selectRolePermissionByUserId(Long userId);
}
