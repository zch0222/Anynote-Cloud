package com.anynote.note.model.bo;

import lombok.*;

/**
 * Mooc 参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocParam extends KnowledgeBaseQueryParam {

    /**
     * 慕课id
     */
    private Long moocId;

    public void setKnowledgeBaseId(Long id) {
        this.setId(id);
    }

    public Long getKnowledgeBaseId() {
        return this.getId();
    }

    @Builder(builderMethodName = "MoocParamBuilder")
    public MoocParam(Long moocId, Long knowledgeBaseId) {
        this.moocId = moocId;
        this.setKnowledgeBaseId(knowledgeBaseId);
    }
}
