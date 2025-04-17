package com.anynote.note.api.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserKnowledgeBaseUpdateDTO {

    @NotNull(message = "知识库id不能为空")
    private Long knowledgeBaseId;

    @NotNull(message = "用户id不能为空")
    private Long userId;

    @NotNull(message = "权限不能为空")
    @Max(value = 3, message = "权限错误")
    @Min(value = 1, message = "权限错误")
    private Integer permissions;
}
