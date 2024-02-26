package com.anynote.note.model.dto;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserNoteTaskAnalyzeDTO {
    /**
     * 知识库ID
     */
    @NotNull(message = "知识库ID不能为空")
    private Long knowledgeBaseId;

    /**
     * 用户id
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码错误")
    private Integer page;

    @NotNull(message = "页面大小错误")
    @Max(value = 100, message = "页面大小错误")
    @Min(value = 1, message = "页面大小错误")
    private Integer pageSize;
}
