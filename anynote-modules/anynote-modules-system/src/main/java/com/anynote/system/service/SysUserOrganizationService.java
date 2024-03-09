package com.anynote.system.service;

import com.anynote.system.api.model.po.SysUserOrganization;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysUserOrganizationService extends IService<SysUserOrganization> {

    /**
     * 获取用户组织信息
     * @param userId 用户id
     * @param organizationId 组织id
     * @return 用户组织信息
     */
    public SysUserOrganization getSysUserOrganization(Long userId, Long organizationId);
}
