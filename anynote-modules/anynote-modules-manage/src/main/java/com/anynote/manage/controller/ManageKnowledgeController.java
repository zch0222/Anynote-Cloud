package com.anynote.manage.controller;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.manage.service.ManageKnowledgeBaseService;
import com.anynote.note.api.model.dto.NoteKnowledgeBaseDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anynote.core.utils.ResUtil;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("bases")
@Validated
public class ManageKnowledgeController {

    @Resource
    private ManageKnowledgeBaseService manageKnowledgeBaseService;


    /**
     * 获取知识库列表
     * @param page 页码
     * @param pageSize 页面大小
     * @param type 类型
     * @param status 状态
     * @param organizationId 组织id
     * @return
     */
    @GetMapping
    public ResData<PageBean<NoteKnowledgeBaseDTO>> getKnowledgeBaseList(@NotNull(message = "页码不能为空") Integer page,
                                                                        @NotNull(message = "页面大小不能为空") Integer pageSize, Integer type,
                                                                        Integer status, Long organizationId) {
        return ResUtil.success(manageKnowledgeBaseService.getKnowledgeBaseList(page, pageSize, type, status, organizationId));
    }
}
