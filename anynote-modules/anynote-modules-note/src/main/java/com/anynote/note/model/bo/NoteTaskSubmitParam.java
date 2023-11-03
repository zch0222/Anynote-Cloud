package com.anynote.note.model.bo;

import com.anynote.note.model.dto.NoteTaskSubmissionRecordCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteTaskSubmitParam extends NoteQueryParam{
    private Long taskId;

    public NoteTaskSubmitParam(NoteTaskSubmissionRecordCreateDTO
                                                       noteTaskSubmissionRecordCreateDTO) {
        this.taskId = noteTaskSubmissionRecordCreateDTO.getNoteTaskId();
        this.setId(noteTaskSubmissionRecordCreateDTO.getNoteId());
    }

    public Long getNoteId() {
        return this.getId();
    }
}
