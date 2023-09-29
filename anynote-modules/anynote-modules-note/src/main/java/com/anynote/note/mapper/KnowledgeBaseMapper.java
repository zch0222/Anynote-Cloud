package com.anynote.note.mapper;

import com.anynote.common.datascope.annotation.DataScopeInterceptor;
import com.anynote.note.api.model.po.NoteKnowledgeBase;
import com.anynote.note.model.bo.KnowledgeBaseQueryParam;
import com.anynote.note.model.dto.NoteKnowledgeBaseDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 知识库 Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface KnowledgeBaseMapper extends BaseMapper<NoteKnowledgeBase> {

    @DataScopeInterceptor
    public List<NoteKnowledgeBaseDTO> selectOrganizationKnowledgeBaseList(KnowledgeBaseQueryParam param);

    @DataScopeInterceptor
    public NoteKnowledgeBaseDTO selectKnowledgeBaseById(KnowledgeBaseQueryParam param);

}
