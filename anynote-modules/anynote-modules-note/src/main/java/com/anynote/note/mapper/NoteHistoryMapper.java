package com.anynote.note.mapper;

import com.anynote.note.api.model.po.NoteHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 笔记历史记录 Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface NoteHistoryMapper extends BaseMapper<NoteHistory> {
}
