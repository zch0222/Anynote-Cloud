package com.anynote.note.enums;

/**
 * @author 称霸幼儿园
 */
public enum NoteOperationType {
    /**
     * 编辑
     */
    EDIT(1),

    /**
     * 管理
     */
    MANGE(2),

    /**
     * 评价
     */
    EVALUATE(3)
    ;

    private final int value;

    NoteOperationType(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
