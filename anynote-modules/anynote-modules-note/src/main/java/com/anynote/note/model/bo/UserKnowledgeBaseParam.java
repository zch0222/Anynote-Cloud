package com.anynote.note.model.bo;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserKnowledgeBaseParam extends KnowledgeBaseQueryParam {

    private Long userId;

    private Integer permissions;

    public Long getKnowledgeBaseId() {
        return this.getId();
    }

    public void setKnowledgeBaseId(Long knowledgeBaseId) {
        this.setId(knowledgeBaseId);
    }

    @Builder(builderMethodName = "UserKnowledgeBaseParamBuilder")
    public UserKnowledgeBaseParam(Long userId, Integer permissions, Long knowledgeBaseId) {
        this.userId = userId;
        this.permissions = permissions;
        this.setKnowledgeBaseId(knowledgeBaseId);
    }


}
