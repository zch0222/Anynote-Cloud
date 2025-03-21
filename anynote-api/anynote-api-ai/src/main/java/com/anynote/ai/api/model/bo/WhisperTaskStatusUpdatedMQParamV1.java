package com.anynote.ai.api.model.bo;

import com.anynote.ai.api.enums.WhisperTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhisperTaskStatusUpdatedMQParamV1 {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        /**
         * 文本
         */
        private String text;

        /**
         * 字幕文件链接
         */
        private String srt;

        /**
         * txt文件链接
         */
        private String txt;
    }

    private Long taskId;

    /**
     * 任务状态
     */
    private WhisperTaskStatus status;

    /**
     * 任务结果
     */
    private Result result;

    private String errorMessage;
}
