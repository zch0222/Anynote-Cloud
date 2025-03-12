package com.anynote.note.model.bo;

import lombok.*;

/**
 * Mooc Item 参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocItemParam extends MoocParam {

    /**
     * 慕课item id
     */
    private Long moocItemId;

    @Builder(builderMethodName = "MoocItemParamBuilder")
    public MoocItemParam(Long moocItemId, Long moocId, Long knowledgeBaseId) {
        super(moocId, knowledgeBaseId);
        this.moocItemId = moocItemId;
    }
}
