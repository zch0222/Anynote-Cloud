package com.anynote.note.mapper;

import com.anynote.note.api.model.po.NoteOperationLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 笔记操作日志Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface NoteOperationLogMapper extends BaseMapper<NoteOperationLog> {
}
