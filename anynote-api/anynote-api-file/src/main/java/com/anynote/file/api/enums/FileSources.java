package com.anynote.file.api.enums;

/**
 * 文件来源
 * @author 称霸幼儿园
 */
public enum FileSources {

    /**
     * 笔记图片
     */
    NOTE_IMAGE(0),


    /**
     * 知识库封面
     */
    KNOWLEDGE_BASE_COVER(1),

    /**
     * 知识库文档
     */
    KNOWLEDGE_BASE_DOC(2),

    /**
     * 知识库用户导入名单
     */
    KNOWLEDGE_BASE_MEMBER_EXCEL(3)
    ;

    private final int value;

    FileSources(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
