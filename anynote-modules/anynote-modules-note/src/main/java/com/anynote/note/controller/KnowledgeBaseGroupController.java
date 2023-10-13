package com.anynote.note.controller;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.CreateResEntity;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.api.model.po.KnowledgeBaseGroup;
import com.anynote.note.constant.NoteErrorMessageConstants;
import com.anynote.note.model.bo.KnowledgeBaseGroupCreateParam;
import com.anynote.note.model.bo.KnowledgeBaseGroupQueryParam;
import com.anynote.note.model.dto.KnowledgeBaseGroupCreateDTO;
import com.anynote.note.service.KnowledgeBaseGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("baseGroups")
@Validated
public class KnowledgeBaseGroupController {


    @Autowired
    private KnowledgeBaseGroupService knowledgeBaseGroupService;

    @GetMapping
    public ResData<List<KnowledgeBaseGroup>> getKnowledgeBaseGroups(@NotNull(message = NoteErrorMessageConstants.KNOWLEDGE_BASE_ID_NULL)
                                                              Long knowledgeBaseId,
                                                              Long parentGroupId) {
        KnowledgeBaseGroupQueryParam groupQueryParam = KnowledgeBaseGroupQueryParam.GroupQueryParamBuilder()
                .knowledgeBaseId(knowledgeBaseId)
                .knowledgeBaseGroupParentId(parentGroupId)
                .build();
        return ResUtil.success(knowledgeBaseGroupService.getKnowledgeBaseGroups(groupQueryParam));
    }

    @PostMapping
    public ResData<CreateResEntity> createKnowledgeBaseGroup(@Valid @RequestBody KnowledgeBaseGroupCreateDTO groupCreateDTO) {
        KnowledgeBaseGroupCreateParam groupCreateParam = KnowledgeBaseGroupCreateParam.GroupCreateParamBuilder()
                .groupName(groupCreateDTO.getGroupName())
                .knowledgeBaseId(groupCreateDTO.getKnowledgeBaseId())
                .build();
        return ResUtil.success(CreateResEntity.builder()
                .id(knowledgeBaseGroupService.createKnowledgeBaseGroup(groupCreateParam))
                .build());
    }
}
