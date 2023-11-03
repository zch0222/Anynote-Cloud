package com.anynote.note.mapper;

import com.anynote.note.api.model.po.NoteTaskOperationHistory;
import com.anynote.note.model.vo.NoteTaskHistoryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Mapper
public interface NoteTaskOperationHistoryMapper extends BaseMapper<NoteTaskOperationHistory> {

    /**
     * 获取笔记任务历史
     * @param userId 用户id
     * @param noteTaskId 笔记任务id
     * @return 该用户的该笔记任务的历史
     */
    public List<NoteTaskHistoryVO> selectNoteTaskHistoryList(@Param("userId") Long userId,
                                                          @Param("noteTaskId") Long noteTaskId);
}
