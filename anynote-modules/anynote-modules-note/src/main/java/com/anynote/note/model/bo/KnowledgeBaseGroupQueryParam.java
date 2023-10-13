package com.anynote.note.model.bo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
public class KnowledgeBaseGroupQueryParam extends KnowledgeBaseQueryParam {

    private Long knowledgeBaseGroupId;

    private Long knowledgeBaseGroupParentId;

    @Builder(builderMethodName = "GroupQueryParamBuilder")
    public KnowledgeBaseGroupQueryParam(Long knowledgeBaseId, Long knowledgeBaseGroupId,
                                        Long knowledgeBaseGroupParentId) {
        this.setKnowledgeBaseId(knowledgeBaseId);
        this.knowledgeBaseGroupParentId = knowledgeBaseGroupParentId;
        this.knowledgeBaseGroupId = knowledgeBaseGroupId;
    }

    public void setKnowledgeBaseId(Long id) {
        this.setId(id);
    }

    public Long getKnowledgeBaseId() {
        return this.getId();
    }
}
