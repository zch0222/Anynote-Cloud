package com.anynote.note.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class MoocListDTO {

    /**
     * 知识库id
     */
    @NotNull(message = "知识库id不能为空")
    private Long knowledgeId;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码错误")
    private Integer page;

    /**
     * 页面大小
     */
    @NotNull(message = "页码大小不能为空")
    @Min(value = 1, message = "页面大小错误")
    @Max(value = 50, message = "页码大小错误")
    private Integer pageSize;
}
