package com.anynote.common.rocketmq.tags;

/**
 *
 * @author 称霸幼儿园
 */
public enum NoteTaskTagsEnum {
    SUBMIT("提交任务")
    ;

    private final String description;

    NoteTaskTagsEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
