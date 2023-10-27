package com.anynote.note.model.bo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
public class NoteTaskSubmissionRecordQueryParam extends NoteTaskQueryParam {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 笔记id
     */
    private Long noteId;

    /**
     * 提交时间
     */
    private Date submitTime;
}
