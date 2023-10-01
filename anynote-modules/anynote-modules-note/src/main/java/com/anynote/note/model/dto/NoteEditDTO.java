package com.anynote.note.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 笔记编辑传输类
 * @author 称霸幼儿园
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteEditDTO {
    private Long noteId;

    private String title;

    private String content;
}
