package com.anynote.note.model.bo;

import lombok.*;

import java.util.List;

/**
 *
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocItemUpdateParam extends MoocItemParam{

    /**
     * 慕课Item标题
     */
    private String title;

    /**
     * 文件对象名称
     */
    private String objectName;

    /**
     * 父Item id，如果为0表示没有父节点
     */
    private Long parentId;

    /**
     * 项目文本
     */
    private String itemText;

    @Builder(builderMethodName = "MoocItemUpdateParamBuilder")
    public MoocItemUpdateParam(Long moocId, Long moocItemId, String title, String objectName,
                               Long parentId, String itemText) {
        setMoocId(moocId);
        setMoocItemId(moocItemId);
        this.title = title;
        this.objectName = objectName;
        this.parentId = parentId;
        this.itemText = itemText;
    }
}
