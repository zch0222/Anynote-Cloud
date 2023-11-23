package com.anynote.note.mapper;

import com.anynote.note.api.model.po.Doc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文档 Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface DocMapper extends BaseMapper<Doc> {
}
