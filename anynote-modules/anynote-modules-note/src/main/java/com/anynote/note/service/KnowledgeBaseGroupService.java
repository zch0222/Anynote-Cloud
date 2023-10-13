package com.anynote.note.service;

import com.anynote.note.api.model.po.KnowledgeBaseGroup;
import com.anynote.note.model.bo.KnowledgeBaseGroupCreateParam;
import com.anynote.note.model.bo.KnowledgeBaseGroupQueryParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 称霸幼儿园
 */
public interface KnowledgeBaseGroupService extends IService<KnowledgeBaseGroup> {

    public List<KnowledgeBaseGroup> getKnowledgeBaseGroups(KnowledgeBaseGroupQueryParam groupQueryParam);

    public Long createKnowledgeBaseGroup(KnowledgeBaseGroupCreateParam groupCreateParam);
}
