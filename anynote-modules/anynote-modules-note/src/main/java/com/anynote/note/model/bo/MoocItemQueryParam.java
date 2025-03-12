package com.anynote.note.model.bo;

import lombok.*;

/**
 * 慕课Item Query Param
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocItemQueryParam extends MoocItemParam {

    /**
     * item 父节点id
     */
    private Long parentId;

    /**
     * 慕课类型对象 0.章节 1.视频 2.文档
     */
    private Integer moocItemType;

    private Integer page;

    private Integer pageSize;

    @Builder(builderMethodName = "MoocItemQueryParamBuilder")
    public MoocItemQueryParam(Long parentId, Integer moocItemType,
                              Integer page, Integer pageSize,
                              Long moocItemId, Long moocId, Long knowledgeBaseId) {
        super(moocItemId, moocId, knowledgeBaseId);
        this.parentId = parentId;
        this.moocItemType = moocItemType;
        this.page = page;
        this.pageSize = pageSize;
    }
}
