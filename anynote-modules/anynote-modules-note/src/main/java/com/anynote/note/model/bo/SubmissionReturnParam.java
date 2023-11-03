package com.anynote.note.model.bo;

import com.anynote.note.api.model.po.NoteTaskSubmissionRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionReturnParam extends NoteTaskQueryParam {

    /**
     * 提交记录
     */
//    private Long submissionRecordId;

    private NoteTaskSubmissionRecord noteTaskSubmissionRecord;


    public SubmissionReturnParam(NoteTaskSubmissionRecord noteTaskSubmissionRecord, Long noteTaskId) {
        this.noteTaskSubmissionRecord = noteTaskSubmissionRecord;
        this.setNoteTaskId(noteTaskId);
    }
}
