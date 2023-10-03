package com.anynote.note.model.dto;

import com.anynote.note.api.model.po.NoteTaskSubmissionRecord;
import lombok.Data;

/**
 *
 * @author 称霸幼儿园
 */
@Data
public class NoteTaskSubmissionRecordDTO extends NoteTaskSubmissionRecord {

    /**
     * 用户名
     */
    private String submissionUsername;

    private String submissionNickname;


}
