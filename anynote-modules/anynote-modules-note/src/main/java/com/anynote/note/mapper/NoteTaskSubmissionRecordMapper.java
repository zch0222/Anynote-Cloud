package com.anynote.note.mapper;

import com.anynote.note.api.model.po.NoteTaskSubmissionRecord;
import com.anynote.note.model.bo.NoteTaskSubmissionRecordQueryParam;
import com.anynote.note.model.dto.NoteTaskSubmissionRecordDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Mapper
public interface NoteTaskSubmissionRecordMapper extends
        BaseMapper<NoteTaskSubmissionRecord> {

    public List<NoteTaskSubmissionRecordDTO> selectNoteTaskSubmissionRecordList(NoteTaskSubmissionRecordQueryParam queryParam);
}
