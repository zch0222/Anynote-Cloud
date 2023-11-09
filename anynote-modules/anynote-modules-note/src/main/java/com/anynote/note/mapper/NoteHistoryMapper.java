package com.anynote.note.mapper;

import com.anynote.note.api.model.po.NoteHistory;
import com.anynote.note.model.vo.NoteHistoryListItemVO;
import com.anynote.note.model.vo.NoteHistoryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 笔记历史记录 Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface NoteHistoryMapper extends BaseMapper<NoteHistory> {

    /**
     * 选取笔记历史列表
     * @param noteId 笔记id
     * @return
     */
    public List<NoteHistoryListItemVO> selectNoteHistoryListItemVOList(@Param("noteId") Long noteId);

    public NoteHistoryVO selectNoteHistory(@Param("operationId") Long operationId);


}
