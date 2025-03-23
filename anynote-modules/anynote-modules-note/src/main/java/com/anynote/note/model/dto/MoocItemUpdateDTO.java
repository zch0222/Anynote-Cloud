package com.anynote.note.model.dto;

import com.anynote.note.model.bo.MoocItemCreateParam;
import com.anynote.note.model.bo.MoocItemUpdateParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MoocItemUpdateDTO {

    /**
     * 慕课id
     */
    @NotNull(message = "慕课id不能为空")
    private Long moocId;

    /**
     * 知识库id
     */
    @NotNull(message = "知识库id")
    private Long knowledgeBaseId;

//    @NotNull(message = "慕课Item id不能为空")
//    private Long moocItemId;

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

}
