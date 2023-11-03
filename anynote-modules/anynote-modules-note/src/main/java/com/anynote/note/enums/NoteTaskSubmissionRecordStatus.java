package com.anynote.note.enums;

/**
 *
 * @author 称霸幼儿园
 */
public enum NoteTaskSubmissionRecordStatus {

    /**
     * 正常
     */
    NORMAL(0),

    /**
     * 被退回
     */
    RETURNED(1)
    ;


    private final int value;

    NoteTaskSubmissionRecordStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
