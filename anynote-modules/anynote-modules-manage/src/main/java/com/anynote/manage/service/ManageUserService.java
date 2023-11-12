package com.anynote.manage.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.system.api.model.bo.SysUserUpdateParam;
import com.anynote.system.api.model.po.SysUser;

/**
 * @author 称霸幼儿园
 */
public interface ManageUserService {

    /**
     * 获取用户列表
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean<SysUser> getUserList(Integer page, Integer pageSize);

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    public SysUser getSysUserInfoById(Long userId);


    public Integer updateSysUser(SysUserUpdateParam updateParam);
}
