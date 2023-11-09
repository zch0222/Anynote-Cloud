package com.anynote.note.service.impl;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.api.model.po.Note;
import com.anynote.note.api.model.po.NoteHistory;
import com.anynote.note.datascope.annotation.RequiresNotePermissions;
import com.anynote.note.enums.NotePermissions;
import com.anynote.note.mapper.NoteHistoryMapper;
import com.anynote.note.model.bo.NoteHistoryListItemQueryParam;
import com.anynote.note.model.bo.NoteHistoryQueryParam;
import com.anynote.note.model.vo.NoteHistoryListItemVO;
import com.anynote.note.model.vo.NoteHistoryVO;
import com.anynote.note.service.NoteHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
                .noteId(note.getId())
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


    @Override
    public NoteHistory getLatestNoteHistory(Long noteId) {
        LambdaQueryWrapper<NoteHistory> noteHistoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noteHistoryLambdaQueryWrapper
                .eq(NoteHistory::getNoteId, noteId)
                .orderByDesc(NoteHistory::getCreateTime)
                .last("LIMIT 0, 1");
        List<NoteHistory> noteHistoryList = this.baseMapper.selectList(noteHistoryLambdaQueryWrapper);
        if (1 != noteHistoryList.size()) {
            throw new BusinessException("获取笔记历史失败");
        }
        return noteHistoryList.get(0);
    }

    @RequiresNotePermissions(NotePermissions.READ)
    @Override
    public PageBean<NoteHistoryListItemVO> getNoteHistoryListItemVOList(NoteHistoryListItemQueryParam queryParam) {
        PageHelper.startPage(queryParam.getPage(), queryParam.getPageSize(), "operation_time DESC");
        List<NoteHistoryListItemVO> rows = this.baseMapper.selectNoteHistoryListItemVOList(queryParam.getNoteId());
        PageInfo<NoteHistoryListItemVO> pageInfo = new PageInfo<>(rows);
        return PageBean.<NoteHistoryListItemVO>builder()
                .total(pageInfo.getTotal())
                .rows(rows)
                .pages(pageInfo.getPages())
                .current(queryParam.getPage())
                .build();
    }


    @RequiresNotePermissions(NotePermissions.READ)
    @Override
    public NoteHistoryVO getNoteHistory(NoteHistoryQueryParam noteHistoryQueryParam) {
        return this.baseMapper.selectNoteHistory(noteHistoryQueryParam.getOperationId());
    }
}
