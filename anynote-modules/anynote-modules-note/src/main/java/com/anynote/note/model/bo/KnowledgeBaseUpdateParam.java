package com.anynote.note.model.bo;

import com.anynote.note.model.dto.KnowledgeBaseUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 知识库更新参数
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeBaseUpdateParam extends KnowledgeBaseQueryParam {
    private String detail;

    private String cover;

    public KnowledgeBaseUpdateParam(KnowledgeBaseUpdateDTO knowledgeBaseUpdateDTO) {
        this.setId(knowledgeBaseUpdateDTO.getKnowledgeBaseId());
        this.setName(knowledgeBaseUpdateDTO.getName());
        this.detail = knowledgeBaseUpdateDTO.getDetail();
        this.cover = knowledgeBaseUpdateDTO.getCover();
    }
}
