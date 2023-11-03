package com.anynote.note.service;

import com.anynote.note.api.model.po.NoteTaskOperationHistory;
import com.anynote.note.enums.NoteTaskOperationType;
import com.anynote.note.model.vo.NoteTaskHistoryVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface NoteTaskOperationHistoryService extends IService<NoteTaskOperationHistory> {

    public void asyncSaveNoteTaskOperationHistory(NoteTaskOperationHistory noteTaskOperationHistory);


    public List<NoteTaskHistoryVO> getNoteTaskHistoryList(Long userId, Long noteTaskId);
}
