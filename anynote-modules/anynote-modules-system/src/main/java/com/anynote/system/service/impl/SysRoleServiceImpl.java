package com.anynote.system.service.impl;

import com.anynote.system.mapper.SysRoleMapper;
import com.anynote.system.api.model.po.SysRole;
import com.anynote.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 角色服务
 *
 * @author 称霸幼儿园
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        return this.baseMapper.selectRolePermissionByUserId(userId);
    }
}
