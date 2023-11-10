package com.anynote.manage.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.api.model.dto.NoteKnowledgeBaseDTO;

public interface ManageKnowledgeBaseService {

    public PageBean<NoteKnowledgeBaseDTO> getKnowledgeBaseList(Integer page, Integer pageSize);
}
