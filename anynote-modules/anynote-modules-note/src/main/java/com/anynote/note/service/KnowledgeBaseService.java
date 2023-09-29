package com.anynote.note.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.api.model.po.NoteKnowledgeBase;
import com.anynote.note.model.dto.NoteKnowledgeBaseDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 知识库服务
 * @author 称霸幼儿园
 */
public interface KnowledgeBaseService extends IService<NoteKnowledgeBase> {

    public NoteKnowledgeBaseDTO getKnowledgeBaseById(Long id);

    public PageBean<NoteKnowledgeBaseDTO> getUsersOrganizationKnowledgeBase(Integer page, Integer pageSize);

    public List<NoteKnowledgeBaseDTO> selectOrganizationKnowledgeBasesByUserIdByPage(Long userId);


}
