package com.anynote.note.model.bo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author 称霸幼儿园
 */
@Data
public class NoteTaskSubmissionRecordQueryParam extends KnowledgeBaseQueryParam {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 笔记任务id
     */
    private Long noteTaskId;

    /**
     * 笔记id
     */
    private Long noteId;

    /**
     * 提交时间
     */
    private Date submitTime;
}
