package com.anynote.note.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MoocCreateDTO {

    /**
     * 慕课标题
     */
    @NotNull(message = "请输入慕课标题")
    @Size(min = 3, max = 500, message = "慕课标题长度必须在3~500")
    private String title;

    @NotNull(message = "请选择慕课封面")
    private String cover;

    /**
     * 慕课描述
     */
    @Size(max = 2000, message = "慕课描述必须在2000个字符以内")
    private String moocDescription;

    @NotNull(message = "请选择知识库")
    private Long knowledgeBaseId;

    @Max(value = 3, message = "可见性权限错误")
    @Min(value = 1, message = "可见性权限错误")
    private Integer dataScope;

}
