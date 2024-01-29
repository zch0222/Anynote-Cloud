package com.anynote.note.model.dto;

import com.anynote.file.api.model.dto.CompleteUploadDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
public class CompleteNoteImageUploadDTO extends CompleteUploadDTO {

    /**
     * 笔记ID
     */
    @NotNull(message = "笔记ID不能为空")
    private Long noteId;
}