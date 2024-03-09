package com.anynote.note.service;

import com.anynote.note.api.model.po.NoteOperationLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 笔记操作日志服务
 * @author 称霸幼儿园
 */
public interface NoteOperationLogService extends IService<NoteOperationLog> {

    public Long selectNoteEditCount(Long noteId);
}
