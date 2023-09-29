package com.anynote.note.controller;

import com.anynote.common.datascope.annotation.DataScope;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.api.model.po.NoteKnowledgeBase;
import com.anynote.note.model.dto.NoteKnowledgeBaseDTO;
import com.anynote.note.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author 称霸幼儿园
 */
@RequestMapping("/bases")
@RestController
@Validated
public class KnowledgeBaseController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @DataScope
    @GetMapping("organizations")
    public ResData<PageBean<NoteKnowledgeBaseDTO>> getUsersOrganizationKnowledgeBases(@NotNull(message = "页码不能为空") Integer page,
                                                                                      @NotNull(message = "页面容量不能为空") Integer pageSize) {
        return ResData.success(knowledgeBaseService
                .getUsersOrganizationKnowledgeBase(page, pageSize));
    }


    @DataScope(userAlias = "sys_user",
            organizationAlias = "sys_organization")
    @GetMapping("{id}")
    public ResData<NoteKnowledgeBaseDTO> getKnowledgeBaseById(@NotNull(message = "知识库id不能为空") @PathVariable Long id) {
        return ResData.success(knowledgeBaseService.getKnowledgeBaseById(id));
    }



}
