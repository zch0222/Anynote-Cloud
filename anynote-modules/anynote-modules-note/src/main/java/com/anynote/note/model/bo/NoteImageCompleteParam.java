package com.anynote.note.model.bo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 称霸幼儿园
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class NoteImageCompleteParam extends NoteQueryParam {


    /**
     * 上传ID
     */
    private String uploadId;

    /**
     * 文件哈希
     */
    private String hash;

    @Builder(builderMethodName = "NoteImageCompleteParamBuilder")
    public NoteImageCompleteParam(String uploadId, String hash, Long noteId) {
        this.uploadId = uploadId;
        this.hash = hash;
        this.setId(noteId);
    }

    public void setNoteId(Long id) {
        this.setId(id);
    }

    public Long getNoteId() {
        return this.getId();
    }
}
