package com.anynote.note.enums;

public enum DocType {
    /**
     * 笔记图片
     */
    PDF(0);

    private final int value;

    DocType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
