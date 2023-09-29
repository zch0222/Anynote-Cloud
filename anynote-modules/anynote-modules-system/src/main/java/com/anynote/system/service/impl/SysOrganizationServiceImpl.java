package com.anynote.system.service.impl;

import com.anynote.system.api.model.po.SysOrganization;
import com.anynote.system.mapper.SysOrganizationMapper;
import com.anynote.system.service.SysOrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 机构服务
 * @author 称霸幼儿园
 */
@Service
public class SysOrganizationServiceImpl extends ServiceImpl<SysOrganizationMapper, SysOrganization>
        implements SysOrganizationService {
}
