package com.anynote.note.enums;

public enum UserNoteTaskStatus {

    /**
     * 未提交
     */
    NOT_SUBMITTED(0),

    /**
     * 已提交
     */
    SUBMITTED(1),

    /**
     * 无需提交
     */
    NO_SUBMISSION_REQUIRED(2),

    /**
     * 被退回
     */
    RETURNED(3);

    private final int value;

    UserNoteTaskStatus(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
