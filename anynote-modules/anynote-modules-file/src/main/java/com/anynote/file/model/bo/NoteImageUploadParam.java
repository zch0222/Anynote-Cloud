package com.anynote.file.model.bo;

import com.anynote.file.api.model.bo.FileUploadParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteImageUploadParam extends FileUploadParam {

    private Long noteId;
}
