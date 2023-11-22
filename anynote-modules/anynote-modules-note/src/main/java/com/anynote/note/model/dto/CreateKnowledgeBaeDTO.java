package com.anynote.note.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 称霸幼儿园
 */
@Data
public class CreateKnowledgeBaeDTO {

    /**
     * 知识库名称
     */
    @Size(max = 15, min = 2, message = "知识库名称必须在2～15字")
    private String name;

    /**
     * 知识库封面id
     */
    @NotNull(message = "知识库封面不能为空")
    private String cover;

    /**
     * 类型 (0.普通知识库 1.课程知识库)
     */
    @NotNull(message = "知识库类型错误")
    @Max(value = 1, message = "知识库类型错误")
    @Min(value = 0, message = "知识库类型错误")
    private Integer type;

    /**
     * 知识库描述
     */
    @Size(max = 500, message = "知识库描述不能超过500字")
    private String detail;


}
