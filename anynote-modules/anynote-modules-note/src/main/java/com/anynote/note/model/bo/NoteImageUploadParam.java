package com.anynote.note.model.bo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 称霸幼儿园
 */
@Data
public class NoteImageUploadParam extends NoteQueryParam {

    private MultipartFile image;

    private String uploadId;

    public Long getNoteId() {
        return this.getId();
    }

    public void setNoteId(Long id) {
        this.setId(id);
    }
}
