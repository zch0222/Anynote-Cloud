package com.anynote.note.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 *
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteTaskSubmissionRecordCreateDTO {

    /**
     * 笔记任务id
     */
    @NotNull(message = "任务id不能为空")
    private Long noteTaskId;

    /**
     * 笔记id
     */
    @NotNull(message = "笔记id不能为空")
    private Long noteId;

}
