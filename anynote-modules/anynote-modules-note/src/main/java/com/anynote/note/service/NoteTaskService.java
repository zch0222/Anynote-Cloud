package com.anynote.note.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.api.model.po.Note;
import com.anynote.note.api.model.po.NoteTask;
import com.anynote.note.api.model.po.NoteTaskSubmissionRecord;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.dto.AdminNoteTaskDTO;
import com.anynote.note.model.dto.MemberNoteTaskDTO;
import com.anynote.note.model.dto.NoteTaskSubmissionRecordDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 称霸幼儿园
 */
public interface NoteTaskService extends IService<NoteTask> {

    public Long createNoteTask(NoteTaskCreateParam taskCreateParam);

    public String submitNoteTask(NoteTaskSubmitParam submitParam);

    public PageBean<AdminNoteTaskDTO> getAdminNoteTasks(NoteTaskQueryParam queryParam);

    public PageBean<MemberNoteTaskDTO> getMemberNoteTasks(NoteTaskQueryParam queryParam);

    public Long getNoteTaskNeedSubmitCount(NoteTaskQueryParam queryParam);

}
