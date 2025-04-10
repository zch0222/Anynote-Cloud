package com.anynote.note.model.bo;

import lombok.*;

import java.util.List;

/**
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocItemBatchDeleteItemParam extends MoocParam {

    private List<Long> itemIds;

    @Builder(builderMethodName = "MoocItemBatchDeleteItemParamBuilder")
    public MoocItemBatchDeleteItemParam(List<Long> itemIds, Long moocId) {
        this.itemIds = itemIds;
        setMoocId(moocId);
    }

}
