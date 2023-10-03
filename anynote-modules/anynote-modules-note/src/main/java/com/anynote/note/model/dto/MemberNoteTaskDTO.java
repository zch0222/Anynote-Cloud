package com.anynote.note.model.dto;

import com.anynote.note.api.model.po.NoteTask;
import lombok.Data;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Data
public class MemberNoteTaskDTO extends NoteTask {

    /**
     * 提交状态 0.已提交 1.未提交
     */
    private Integer submissionStatus;

    /**
     * 提交的笔记的id
     */
    private Long submissionNoteId;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 任务创建者用户名
     */
    private String taskCreatorUsername;

    /**
     * 创建者昵称
     */
    private String taskCreatorNickname;
}
