package com.anynote.note.model.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KnowledgeBaseUsersDeleteParam extends KnowledgeBaseQueryParam {
    private Long userId;

    public Long getKnowledgeBaseId() {
        return this.getId();
    }

    public void setKnowledgeBaseId(Long id) {
        this.setId(id);
    }
}
