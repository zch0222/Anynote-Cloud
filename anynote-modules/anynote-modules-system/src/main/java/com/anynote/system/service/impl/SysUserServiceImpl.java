package com.anynote.system.service.impl;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.bo.Token;
import com.anynote.system.api.model.po.SysUser;
import com.anynote.system.mapper.SysUserMapper;
import com.anynote.system.service.SysPermissionService;
import com.anynote.system.service.SysRoleService;
import com.anynote.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 用户服务
 * @author 称霸幼儿园
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return
     */
    @Override
    public LoginUser getUserInfo(String username) {
        SysUser sysUser = this.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser)) {
            throw new BusinessException(ResCode.USER_NOT_FOUND);
        }

        // 获取角色的权限列表
        Set<String> permissions = sysPermissionService.getRolePermission(sysUser);

        String roleKey = sysRoleService.selectRoleKeysByUserId(sysUser.getId());

        return new LoginUser(sysUser, permissions, roleKey, true);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return this.baseMapper.selectUserByUserName(userName);
    }
}
