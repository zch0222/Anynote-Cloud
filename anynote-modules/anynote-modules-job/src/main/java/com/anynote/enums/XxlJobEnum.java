package com.anynote.enums;

public enum XxlJobEnum {
    /**
     * 慕课ARS
     */
    MOOC_ASR(2);

    /**
     * 任务id
     */
    private final int value;

    XxlJobEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
