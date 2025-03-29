package com.anynote.ai.nio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteTaskSubmissionAnalyzeDTO {

    /**
     * 笔记任务ID
     */
    @NotNull(message = "笔记任务id不能为空")
    private Long noteTaskId;

    @NotNull(message = "模型不能为空")
    private String model;
}
