package com.anynote.note.model.bo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 称霸幼儿园
 */
@Data
public class NoteImageUploadParam extends NoteQueryParam {

    private MultipartFile image;
}
