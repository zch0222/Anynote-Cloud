package com.anynote.note.mapper;

import com.anynote.note.api.model.po.NoteText;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 笔记文本 Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface NoteTextMapper extends BaseMapper<NoteText> {
}
