package com.anynote.note.model.bo;

import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoteTaskAnalyzeQueryParam extends KnowledgeBaseQueryParam {

    /**
     * 知识库ID
     */
    private Long knowledgeBaseId;

    /**
     * 用户id
     */
    private Long userId;

    private Integer page;

    private Integer pageSize;

    @Builder(builderMethodName = "NoteTaskAnalyzeQueryParamBuilder")
    public NoteTaskAnalyzeQueryParam(Long knowledgeBaseId, Long userId, Integer page, Integer pageSize) {
        this.knowledgeBaseId = knowledgeBaseId;
        this.userId = userId;
        this.setPage(page);
        this.setPageSize(pageSize);
        // 用于鉴权
        this.setId(knowledgeBaseId);
    }

}
