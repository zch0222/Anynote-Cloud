package com.anynote.note.enums;

public enum NoteTaskOperationType {

    /**
     * 创建任务
     */
    CREATE(1),

    /**
     * 修改任务
     */
    EDIT(2),

    /**
     * 提交任务
     */
    SUBMIT(3),

    /**
     * 退回提交
     */
    RETURN_SUBMISSION(4),

    /**
     * 添加任务成员
     */
    ADD_USER(5)
    ;

    private final int value;

    NoteTaskOperationType(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
