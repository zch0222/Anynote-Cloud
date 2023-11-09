package com.anynote.note.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.apache.bcel.classfile.LineNumber;

/**
 * @author 称霸幼儿园
 */
@NoArgsConstructor
@Data
public class NoteHistoryListItemQueryParam extends NoteQueryParam {


    @Builder(builderMethodName = "NoteHistoryListItemQueryParamBuilder")
    public NoteHistoryListItemQueryParam(Long noteId, Integer page, Integer pageSize) {
        this.setId(noteId);
        this.setPage(page);
        this.setPageSize(pageSize);
    }

    public Long getNoteId() {
        return this.getId();
    }

    public void setNoteId(Long noteId) {
        this.setNoteId(noteId);
    }

}
