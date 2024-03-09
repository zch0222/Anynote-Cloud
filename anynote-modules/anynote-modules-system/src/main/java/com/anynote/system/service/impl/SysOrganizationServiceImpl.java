package com.anynote.system.service.impl;

import com.anynote.core.constant.POConstants;
import com.anynote.core.constant.SysOrganizationConstants;
import com.anynote.core.utils.StringUtils;
import com.anynote.system.api.model.bo.KnowledgeBaseImportUser;
import com.anynote.system.api.model.po.SysOrganization;
import com.anynote.system.api.model.po.SysUserOrganization;
import com.anynote.system.mapper.SysOrganizationMapper;
import com.anynote.system.service.SysOrganizationService;
import com.anynote.system.service.SysUserOrganizationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机构服务
 * @author 称霸幼儿园
 */
@Service
public class SysOrganizationServiceImpl extends ServiceImpl<SysOrganizationMapper, SysOrganization>
        implements SysOrganizationService {

    @Resource
    private SysUserOrganizationService sysUserOrganizationService;

    @Override
    public void associateKnowledgeUserOrganization(List<KnowledgeBaseImportUser> knowledgeBaseImportUserList) {
        knowledgeBaseImportUserList.stream().forEach(
                knowledgeBaseImportUser -> {
                    if (StringUtils.isNull(knowledgeBaseImportUser.getUserId())) {
                        return;
                    }
                    LambdaQueryWrapper<SysOrganization> sysOrganizationLambdaQueryWrapper =
                            new LambdaQueryWrapper<>();
                    sysOrganizationLambdaQueryWrapper
                            .eq(SysOrganization::getOrganizationName, knowledgeBaseImportUser.getClassName())
                            .select(SysOrganization::getOrganizationName, SysOrganization::getId);
                    SysOrganization sysOrganizationInfo = this.baseMapper.selectOne(sysOrganizationLambdaQueryWrapper);
                    if (StringUtils.isNull(sysOrganizationInfo)) {
                        SysOrganization sysOrganization = SysOrganization.builder()
                                .parentId(SysOrganizationConstants.NO_PARENT_ID)
                                .ancestors(SysOrganizationConstants.NO_ANCESTORS)
                                .organizationName(knowledgeBaseImportUser.getClassName())
                                .orderNum(SysOrganizationConstants.HIGHEST_ORDER_NUM)
                                .status(SysOrganizationConstants.NORMAL_STATUS)
                                .deleted(POConstants.NO_DELETE)
                                .build();
                        this.baseMapper.insert(sysOrganization);
                        this.baseMapper.insertUserSysOrganization(knowledgeBaseImportUser.getUserId(),
                                sysOrganization.getId(), SysOrganizationConstants.USER_ORGANIZATION_STATUS_NORMAL);
                    }
                    else {
                        SysUserOrganization sysUserOrganization =
                                sysUserOrganizationService.getSysUserOrganization(knowledgeBaseImportUser.getUserId(),
                                        sysOrganizationInfo.getId());
                        if (StringUtils.isNull(sysUserOrganization)) {
                            this.baseMapper.insertUserSysOrganization(knowledgeBaseImportUser.getUserId(),
                                    sysOrganizationInfo.getId(), SysOrganizationConstants.USER_ORGANIZATION_STATUS_NORMAL);
                        }
                    }
                }
        );
    }
}
