package com.anynote.note.mapper;


import com.anynote.note.api.model.po.NoteFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 笔记文件Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface NoteFileMapper extends BaseMapper<NoteFile> {
}
