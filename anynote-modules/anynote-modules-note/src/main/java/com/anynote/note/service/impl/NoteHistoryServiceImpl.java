package com.anynote.note.service.impl;

import com.anynote.note.api.model.po.Note;
import com.anynote.note.api.model.po.NoteHistory;
import com.anynote.note.mapper.NoteHistoryMapper;
import com.anynote.note.service.NoteHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Service
public class NoteHistoryServiceImpl extends ServiceImpl<NoteHistoryMapper, NoteHistory>
        implements NoteHistoryService {

    @Override
    public Long saveNoteHistory(Note note, Long operationId, Date historyTime, Long createBy) {
        NoteHistory noteHistory = NoteHistory.builder()
                .operationId(operationId)
                .title(note.getTitle())
                .content(note.getContent())
                .historyTime(historyTime)
                .deleted(0)
                .build();
        Date date = new Date();
        noteHistory.setCreateBy(createBy);
        noteHistory.setCreateTime(date);
        noteHistory.setUpdateBy(createBy);
        noteHistory.setUpdateTime(date);
        this.baseMapper.insert(noteHistory);
        return noteHistory.getId();
    }
}
