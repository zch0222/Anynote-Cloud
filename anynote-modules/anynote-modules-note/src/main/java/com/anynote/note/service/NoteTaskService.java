package com.anynote.note.service;

import com.anynote.note.api.model.po.NoteTask;
import com.anynote.note.model.bo.NoteTaskCreateParam;
import com.anynote.note.model.bo.NoteTaskSubmitParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 称霸幼儿园
 */
public interface NoteTaskService extends IService<NoteTask> {

    public Long createNoteTask(NoteTaskCreateParam taskCreateParam);

    public String submitNoteTask(NoteTaskSubmitParam submitParam);
}
