package com.anynote.note.model.bo;

import lombok.*;

/**
 * 慕课
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocCreateParam extends MoocParam {

    /**
     * 慕课标题
     */
    private String title;

    /**
     * 慕课描述
     */
    private String moocDescription;

    /**
     * 慕课可见性
     */
    private Integer dataScope;

    @Builder(builderMethodName = "MoocCreateParamBuilder")
    public MoocCreateParam(String title, String moocDescription, Long knowledgeBaseId,
                           Integer dataScope) {
        this.title = title;
        this.moocDescription = moocDescription;
        this.dataScope = dataScope;
        this.setKnowledgeBaseId(knowledgeBaseId);
    }

}
