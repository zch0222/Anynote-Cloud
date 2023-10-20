package com.anynote.note.service;

import com.anynote.note.api.model.po.Note;
import com.anynote.note.api.model.po.NoteHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
public interface NoteHistoryService extends IService<NoteHistory> {

    public Long saveNoteHistory(Note note, Long operationId, Date date, Long createBy);
}
