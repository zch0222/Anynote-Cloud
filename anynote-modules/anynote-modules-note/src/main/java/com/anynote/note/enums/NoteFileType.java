package com.anynote.note.enums;

/**
 * 笔记文件类型枚举
 * @author 称霸幼儿园
 */
public enum NoteFileType {

    /**
     * 笔记图片
     */
    NOTE_IMAGE(0);

    private final int value;

    NoteFileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
