package com.anynote.manage.service.impl;

import com.anynote.common.security.utils.SecurityUtils;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.RemoteResDataUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.manage.service.ManageUserService;
import com.anynote.system.api.RemoteUserService;
import com.anynote.system.api.model.bo.SysUserUpdateParam;
import com.anynote.system.api.model.po.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 称霸幼儿园
 */
@Service
public class ManageUserServiceImpl implements ManageUserService {

    @Resource
    private RemoteUserService remoteUserService;

    @Override
    public PageBean<SysUser> getUserList(Integer page, Integer pageSize) {
        ResData<PageBean<SysUser>> resData = remoteUserService.getManageUserList(page, pageSize);
        return RemoteResDataUtil.getResData(resData, "获取用户列表失败");
    }


    @Override
    public SysUser getSysUserInfoById(Long userId) {
        ResData<SysUser> resData = remoteUserService.getSysUserInfoById(userId);
        return RemoteResDataUtil.getResData(resData, "获取用户信息失败");
    }

    @Override
    public Integer updateSysUser(SysUserUpdateParam updateParam) {
        SysUser sysUser = new SysUser();
        sysUser.setId(updateParam.getUserId());
        sysUser.setPassword(SecurityUtils.encryptPassword(updateParam.getPassword()));
        ResData<Integer> resData = remoteUserService.updateSysUser(updateParam.getUserId(), sysUser);
        Integer count = RemoteResDataUtil.getResData(resData, "修改密码失败");
        if (1 != count) {
            throw new BusinessException("未知异常，请联系管理员");
        }
        return count;
    }
}
