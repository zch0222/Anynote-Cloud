package com.anynote.manage.service.impl;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.manage.service.ManageKnowledgeBaseService;
import com.anynote.note.api.model.dto.NoteKnowledgeBaseDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 称霸幼儿园
 */
@Service
public class ManageKnowledgeBaseServiceImpl implements ManageKnowledgeBaseService {

//    @Resource
//


    @Override
    public PageBean<NoteKnowledgeBaseDTO> getKnowledgeBaseList(Integer page, Integer pageSize) {
        return null;
    }
}
