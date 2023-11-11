package com.anynote.manage.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.system.api.model.po.SysUser;

/**
 * @author 称霸幼儿园
 */
public interface ManageUserService {

    public PageBean<SysUser> getUserList(Integer page, Integer pageSize);
}
