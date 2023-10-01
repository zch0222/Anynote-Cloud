package com.anynote.note.model.bo;

import com.anynote.note.model.dto.NoteEditDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Note更新参数
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteUpdateParam extends NoteQueryParam{

    /**
     * 正文
     */
    private String content;

    private Integer dataScope;

    private String permissions;

    private Integer deleted;

    private Long contentId;

    public NoteUpdateParam(NoteEditDTO noteEditDTO) {
        this.setId(noteEditDTO.getNoteId());
        this.setTitle(noteEditDTO.getTitle());
        this.content = noteEditDTO.getContent();
    }
}
