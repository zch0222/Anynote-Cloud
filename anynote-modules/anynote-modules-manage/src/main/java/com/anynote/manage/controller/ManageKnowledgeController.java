package com.anynote.manage.controller;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.manage.service.ManageKnowledgeBaseService;
import com.anynote.note.api.model.dto.NoteKnowledgeBaseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anynote.core.utils.ResUtil;

import javax.annotation.Resource;

/**
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("bases")
public class ManageKnowledgeController {

    @Resource
    private ManageKnowledgeBaseService manageKnowledgeBaseService;


    @GetMapping
    public ResData<PageBean<NoteKnowledgeBaseDTO>> getKnowledgeBaseList() {

    }
}
