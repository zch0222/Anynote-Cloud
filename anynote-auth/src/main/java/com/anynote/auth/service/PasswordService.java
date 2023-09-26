package com.anynote.auth.service;

import com.anynote.system.api.model.po.SysUser;

/**
 * 登录密码服务
 *
 * @author 称霸幼儿园
 */
public interface PasswordService {

    public void validate(SysUser sysUser, String password);
}
