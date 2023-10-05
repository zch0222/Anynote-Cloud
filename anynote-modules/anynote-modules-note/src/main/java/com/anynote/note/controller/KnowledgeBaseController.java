package com.anynote.note.controller;

import com.anynote.common.datascope.annotation.DataScope;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.model.bo.KnowledgeBaseCreateParam;
import com.anynote.note.model.bo.KnowledgeBaseQueryParam;
import com.anynote.note.model.bo.KnowledgeBaseUsersQueryParam;
import com.anynote.note.model.dto.KnowledgeBaseCreateDTO;
import com.anynote.note.model.dto.NoteKnowledgeBaseDTO;
import com.anynote.note.service.KnowledgeBaseService;
import com.anynote.system.api.model.vo.KnowledgeBaseUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResData<PageBean<NoteKnowledgeBaseDTO>> getUsersKnowledgeBases(@NotNull(message = "页码不能为空") Integer page,
                                                                          @NotNull(message = "页面容量不能为空") Integer pageSize) {
        return ResUtil.success(knowledgeBaseService.getUserKnowledgeBases(page, pageSize));
    }

    @GetMapping("users")
    public ResData<PageBean<KnowledgeBaseUserVO>> getKnowledgeBaseUsers(@NotNull(message = "知识库id不能为空") Long knowledgeBaseId,
                                                                        @NotNull(message = "页码不能为空") Integer page,
                                                                        @NotNull(message = "页面容量不能为空") Integer pageSize) {
        KnowledgeBaseUsersQueryParam queryParam = new KnowledgeBaseUsersQueryParam();
        queryParam.setId(knowledgeBaseId);
        queryParam.setPage(page);
        queryParam.setPageSize(pageSize);
        return ResUtil.success(knowledgeBaseService.getKnowledgeBaseUsers(queryParam));
    }

    @PostMapping
    public ResData<Long> createDataBase(@Validated @RequestBody KnowledgeBaseCreateDTO knowledgeBaseCreateDTO) {
        KnowledgeBaseCreateParam knowledgeBaseCreateParam =
                new KnowledgeBaseCreateParam(knowledgeBaseCreateDTO);
        return ResUtil.success(knowledgeBaseService.createKnowledgeBase(knowledgeBaseCreateParam));
    }

    @DataScope(userAlias = "sys_user",
            organizationAlias = "sys_organization")
    @GetMapping("{id}")
    public ResData<NoteKnowledgeBaseDTO> getKnowledgeBaseById(@NotNull(message = "知识库id不能为空") @PathVariable Long id) {
        KnowledgeBaseQueryParam queryParam = KnowledgeBaseQueryParam.builder()
                .id(id)
                .build();
        return ResData.success(knowledgeBaseService.getKnowledgeBaseById(queryParam));
    }





}
