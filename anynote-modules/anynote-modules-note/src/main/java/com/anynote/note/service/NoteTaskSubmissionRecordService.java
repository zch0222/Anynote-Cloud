package com.anynote.note.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.api.model.po.NoteTask;
import com.anynote.note.api.model.po.NoteTaskSubmissionRecord;
import com.anynote.note.model.bo.NoteTaskSubmissionRecordQueryParam;
import com.anynote.note.model.dto.NoteTaskSubmissionRecordDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 称霸幼儿园
 */
public interface NoteTaskSubmissionRecordService
        extends IService<NoteTaskSubmissionRecord> {

    public PageBean<NoteTaskSubmissionRecordDTO> getNoteTaskSubmitRecords(Long noteTaskId, Integer page, Integer pageSize);



}
