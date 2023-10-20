package com.anynote.note.api.model.bo;

import com.anynote.note.api.model.po.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 笔记日志生成
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateNoteEditLogMessage {

    private Long noteId;

    private Date date;

    /**
     * 操作人id
     */
    private Long userId;

    private Note currentNote;

    private Note oldNote;
}
