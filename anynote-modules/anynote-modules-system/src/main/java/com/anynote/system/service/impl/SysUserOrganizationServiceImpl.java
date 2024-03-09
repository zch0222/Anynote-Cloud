package com.anynote.system.service.impl;

import com.anynote.system.api.model.po.SysUserOrganization;
import com.anynote.system.mapper.SysOrganizationMapper;
import com.anynote.system.mapper.SysUserOrganizationMapper;
import com.anynote.system.service.SysUserOrganizationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysUserOrganizationServiceImpl extends ServiceImpl<SysUserOrganizationMapper, SysUserOrganization>
        implements SysUserOrganizationService {


    @Override
    public SysUserOrganization getSysUserOrganization(Long userId, Long organizationId) {

        LambdaQueryWrapper<SysUserOrganization> sysUserOrganizationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserOrganizationLambdaQueryWrapper
                .eq(SysUserOrganization::getOrganizationId, organizationId)
                .eq(SysUserOrganization::getUserId, userId);
        return this.baseMapper.selectOne(sysUserOrganizationLambdaQueryWrapper);
    }
}
