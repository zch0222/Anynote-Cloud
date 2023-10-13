package com.anynote.note.model.bo;

import com.anynote.note.api.model.po.KnowledgeBaseGroup;
import lombok.Builder;
import lombok.Data;

/**
 * @author 称霸幼儿园
 */
@Data
public class KnowledgeBaseGroupCreateParam extends KnowledgeBaseQueryParam {

    private String groupName;

    @Builder(builderMethodName = "GroupCreateParamBuilder")
    public KnowledgeBaseGroupCreateParam(Long knowledgeBaseId, String groupName) {
        this.groupName = groupName;
        this.setKnowledgeBaseId(knowledgeBaseId);
    }

    public void setKnowledgeBaseId(Long id) {
        this.setId(id);
    }

    public Long getKnowledgeBaseId() {
        return this.getId();
    }
}
