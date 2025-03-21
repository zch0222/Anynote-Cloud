package com.anynote.ai.api.enums;

public enum WhisperTaskStatus {

    STARTING(0),

    /**
     * 下载文件对象
     */
    DOWNLOADING_OBJECT(1),

    /**
     * 转码对象
     */
    CONVERTING(2),

    /**
     * 识别中
     */
    WHISPERING(3),

    /**
     * 识别完成
     */
    WHISPER_DONE(4),

    /**
     * 任务成功
     */
    SUCCESS(5),

    /**
     * 任务失败
     */
    FAILED(6);
    ;

    private final int value;

    WhisperTaskStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
