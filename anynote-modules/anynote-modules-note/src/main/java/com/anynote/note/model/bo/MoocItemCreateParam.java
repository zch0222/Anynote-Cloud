package com.anynote.note.model.bo;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocItemCreateParam extends MoocItemParam{

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        /**
         * 慕课Item标题
         */
        private String title;

        /**
         * 慕课类型对象 0.章节 1.视频 2.文档
         */
        private Integer moocItemType;

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
    }

    private List<MoocItemCreateParam.Item> items;

    @Builder(builderMethodName = "MoocItemCreateParamBuilder")
    public MoocItemCreateParam(List<MoocItemCreateParam.Item> items,
                               Long moocId, Long knowledgeBaseId) {
        super(0L, moocId, knowledgeBaseId);
        this.items = items;
    }
}
