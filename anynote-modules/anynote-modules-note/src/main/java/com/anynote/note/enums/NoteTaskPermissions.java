package com.anynote.note.enums;

/**
 * 笔记任务权限
 * @author 称霸幼儿园
 */
public enum NoteTaskPermissions {

    /**
     * 管理任务
     */
    MANAGE(1),

    /**
     * 提交任务
     */
    SUBMIT(2),

    /**
     * 无权限
     */
    NO(3)
    ;

    private final int value;

    NoteTaskPermissions(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
