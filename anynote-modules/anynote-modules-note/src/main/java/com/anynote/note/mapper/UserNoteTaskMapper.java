package com.anynote.note.mapper;

import com.anynote.note.api.model.po.UserNoteTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户笔记任务关联表
 * @author 称霸幼儿园
 */
@Mapper
public interface UserNoteTaskMapper extends BaseMapper<UserNoteTask> {
}
