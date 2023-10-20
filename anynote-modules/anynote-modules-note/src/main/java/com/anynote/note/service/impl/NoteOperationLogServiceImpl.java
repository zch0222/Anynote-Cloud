package com.anynote.note.service.impl;

import com.anynote.note.api.model.po.NoteOperationLog;
import com.anynote.note.mapper.NoteOperationLogMapper;
import com.anynote.note.service.NoteOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 称霸幼儿园
 */
@Service
public class NoteOperationLogServiceImpl extends ServiceImpl<NoteOperationLogMapper, NoteOperationLog>
        implements NoteOperationLogService {
}
