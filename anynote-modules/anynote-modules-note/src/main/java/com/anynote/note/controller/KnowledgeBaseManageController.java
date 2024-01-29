package com.anynote.note.controller;

import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.model.bo.KnowledgeBaseImportUserParam;
import com.anynote.note.model.dto.KnowledgeBaseImportUserVO;
import com.anynote.note.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/manage/bases")
@Validated
public class KnowledgeBaseManageController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @PostMapping("import")
    public ResData<KnowledgeBaseImportUserVO> importUsers(@RequestParam("users") @NotNull(message = "用户表格不能为空") MultipartFile users,
                                                          @RequestParam("knowledgeBaseId") @NotNull(message = "知识库id不能为空") Long knowledgeBaseId) {
        KnowledgeBaseImportUserParam importUserParam = new KnowledgeBaseImportUserParam();
        importUserParam.setId(knowledgeBaseId);
        importUserParam.setExcel(users);
        return ResData.success(knowledgeBaseService.importKnowledgeBaseUser(importUserParam));
    }
}
