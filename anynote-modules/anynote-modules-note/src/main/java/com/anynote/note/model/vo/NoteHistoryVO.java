package com.anynote.note.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Data
public class NoteHistoryVO {
    /**
     * 笔记历史id
     */
    private Long noteHistoryId;

    /**
     * 笔记id
     */
    private Long noteId;

    /**
     * 笔记标题
     */
    private String title;

    /**
     * 笔记内容
     */
    private String content;

    /**
     * 历史时间
     */
    private Date historyTime;

    /**
     * 笔记历史创建者id
     */
    private Long createBy;

    private List<NoteEditVO> noteEditList;

}
