package com.anynote.note.model.bo;

import com.anynote.note.model.dto.KnowledgeBaseCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 知识库创建
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeBaseCreateParam extends KnowledgeBaseQueryParam {
    /**
     * 知识库封面链接
     */
    private String cover;

    /**
     * 知识库描述
     */
    private String detail;

    public KnowledgeBaseCreateParam(KnowledgeBaseCreateDTO knowledgeBaseCreateDTO) {
        this.setName(knowledgeBaseCreateDTO.getName());
        this.detail = knowledgeBaseCreateDTO.getDetail();
        this.cover = knowledgeBaseCreateDTO.getCover();
        this.setType(knowledgeBaseCreateDTO.getType());
        this.setOrganizationId(knowledgeBaseCreateDTO.getOrganizationId());
    }

}
