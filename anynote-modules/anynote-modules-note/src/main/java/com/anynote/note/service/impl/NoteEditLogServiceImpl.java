package com.anynote.note.service.impl;

import com.anynote.note.api.model.po.NoteEditLog;
import com.anynote.note.mapper.NoteEditLogMapper;
import com.anynote.note.service.NoteEditLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 称霸幼儿园
 */
@Service
public class NoteEditLogServiceImpl extends ServiceImpl<NoteEditLogMapper, NoteEditLog>
        implements NoteEditLogService {
}
