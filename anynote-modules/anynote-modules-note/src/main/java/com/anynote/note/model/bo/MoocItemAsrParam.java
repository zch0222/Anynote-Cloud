package com.anynote.note.model.bo;

import lombok.*;

/**
 * 慕课Item语音识别 Param
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocItemAsrParam extends MoocItemParam {

    /**
     * 语言
     */
    private String language;

    @Builder(builderMethodName = "MoocItemAsrParamBuilder")
    public MoocItemAsrParam(Long moocId, Long moocItemId, String language) {
        setMoocId(moocId);
        setMoocItemId(moocItemId);
        this.language = language;
    }
}
