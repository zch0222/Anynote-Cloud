package com.anynote.note.model.bo;

import com.anynote.note.model.dto.NoteCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 笔记创建参数
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteCreateParam extends NoteQueryParam {

    /**
     * 正文
     */
    private String content;

    private Integer dataScope;

    private String permissions;

    private Long contentId;

    public NoteCreateParam(NoteCreateDTO noteCreateDTO) {
        this.setTitle(noteCreateDTO.getTitle());
        this.setKnowledgeBaseId(noteCreateDTO.getKnowledgeBaseId());
    }
}
