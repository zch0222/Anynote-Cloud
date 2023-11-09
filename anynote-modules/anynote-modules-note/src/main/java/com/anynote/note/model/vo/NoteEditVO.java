package com.anynote.note.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Data
public class NoteEditVO {
    /**
     * 笔记编辑记录id
     */
    private Long editLogId;

    /**
     * 原始文本
     */
    private String originalText;

    /**
     * 修改后的文本
     */
    private String revisedText;

    /**
     * 修改类型 0.修改行 1.删除行 2.插入行
     */
    private Integer changeType;

    /**
     * 原始的行号
     */
    private Integer originalPosition;

    /**
     * 新的行号
     */
    private Integer revisedPosition;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 编辑者id
     */
    private Long editorId;

    private String editorUsername;
    private String editorNickname;
}
