package com.anynote.system.service;

import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.po.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户服务
 * @author 称霸幼儿园
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    public LoginUser getUserInfo(String username);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);
}
