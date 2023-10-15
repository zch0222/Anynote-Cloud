package com.anynote.common.rocketmq.tags;

/**
 * @author 称霸幼儿园
 */
public enum NoteTagsEnum {

    SUBMIT("提交任务"),
    GENERATOR_NOTE_INDEX("生成笔记索引")
    ;

    private final String description;

    NoteTagsEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
