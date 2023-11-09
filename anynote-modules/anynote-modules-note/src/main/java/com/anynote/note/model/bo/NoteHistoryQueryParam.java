package com.anynote.note.model.bo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 称霸幼儿园
 */
@Data
public class NoteHistoryQueryParam extends NoteQueryParam {

    /**
     * 操作id
     */
    private Long operationId;

    @Builder(builderMethodName = "NoteHistoryQueryParamBuilder")
    public NoteHistoryQueryParam(Long noteId, Long operationId) {
        this.setId(noteId);
        this.operationId = operationId;
    }

    public Long getNoteId() {
        return this.getId();
    }

    public void setNoteId(Long noteId) {
        this.setId(noteId);
    }

}
