package com.anynote.system.service;

import com.anynote.system.api.model.bo.KnowledgeBaseImportUser;
import com.anynote.system.api.model.po.SysOrganization;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 机构服务
 * @author 称霸幼儿园
 */
public interface SysOrganizationService extends IService<SysOrganization> {

    public void associateKnowledgeUserOrganization(List<KnowledgeBaseImportUser> knowledgeBaseImportUserList);
}
