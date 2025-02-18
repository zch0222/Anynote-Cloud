package com.anynote.note.model.bo;

import lombok.*;

import java.io.Serializable;

/**
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MoocQueryParam extends MoocParam {

    @Builder(builderMethodName = "MoocQueryParamBuilder")
    public MoocQueryParam(Long moocId, Long knowledgeBaseId,
                          Integer page, Integer pageSize) {
        setMoocId(moocId);
        setKnowledgeBaseId(knowledgeBaseId);
        setPage(page);
        setPageSize(pageSize);
    }
}
