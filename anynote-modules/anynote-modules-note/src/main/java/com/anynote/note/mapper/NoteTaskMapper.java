package com.anynote.note.mapper;

import com.anynote.note.api.model.po.NoteTask;
import com.anynote.note.model.bo.NoteTaskQueryParam;
import com.anynote.note.model.dto.MemberNoteTaskDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Mapper
public interface NoteTaskMapper extends BaseMapper<NoteTask> {


    public List<MemberNoteTaskDTO> selectMemberNoteTaskList(NoteTaskQueryParam queryParam);
}
