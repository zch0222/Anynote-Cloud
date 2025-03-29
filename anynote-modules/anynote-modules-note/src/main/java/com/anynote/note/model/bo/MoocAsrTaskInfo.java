package com.anynote.note.model.bo;

import com.anynote.ai.api.enums.WhisperTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 慕课语音识别状态
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoocAsrTaskInfo {

    /**
     * 慕课对象id
     */
    private Long moocItemId;

    /**
     * 慕课id
     */
    private Long moocId;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务状态
     */
    private WhisperTaskStatus taskStatus;

    /**
     * 用户id
     */
    private Long userId;
}
