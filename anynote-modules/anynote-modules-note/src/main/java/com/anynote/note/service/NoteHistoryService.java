package com.anynote.note.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.api.model.po.Note;
import com.anynote.note.api.model.po.NoteHistory;
import com.anynote.note.model.bo.NoteHistoryListItemQueryParam;
import com.anynote.note.model.bo.NoteHistoryQueryParam;
import com.anynote.note.model.vo.NoteHistoryListItemVO;
import com.anynote.note.model.vo.NoteHistoryVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
public interface NoteHistoryService extends IService<NoteHistory> {

    public Long saveNoteHistory(Note note, Long operationId, Date date, Long createBy);

    public NoteHistory getLatestNoteHistory(Long noteId);

    public PageBean<NoteHistoryListItemVO> getNoteHistoryListItemVOList(NoteHistoryListItemQueryParam queryParam);

    public NoteHistoryVO getNoteHistory(NoteHistoryQueryParam noteHistoryQueryParam);
}
