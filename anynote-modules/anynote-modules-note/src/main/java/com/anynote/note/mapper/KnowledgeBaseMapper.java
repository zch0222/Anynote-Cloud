package com.anynote.note.mapper;

import com.anynote.common.datascope.annotation.DataScopeInterceptor;
import com.anynote.note.api.model.po.NoteKnowledgeBase;
import com.anynote.note.model.bo.KnowledgeBaseQueryParam;
import com.anynote.note.api.model.dto.NoteKnowledgeBaseDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
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
    public List<NoteKnowledgeBaseDTO> selectUserKnowledgeBaseList(KnowledgeBaseQueryParam param);


//    @DataScopeInterceptor
    public NoteKnowledgeBaseDTO selectKnowledgeBaseById(KnowledgeBaseQueryParam param);

    public Integer selectUserKnowledgeBasePermissions(@Param("userId") Long userId,
                                                      @Param("knowledgeBaseId") Long knowledgeBaseId);

    public Integer selectUserKnowledgeBasePermissionsByNoteId(@Param("userId") Long userId,
                                                              @Param("noteId") Long noteId);

    public Integer insertUserKnowledgeBase(@Param("userId") Long userId,
                                           @Param("knowledgeBaseId") Long knowledgeBaseId,
                                           @Param("permission") Integer permission);

    public Long selectKnowledgeBaseMembersCount(@Param("knowledgeBaseId") Long knowledgeBaseId);

    public List<NoteKnowledgeBaseDTO> getKnowledgeBaseList(@Param("page") Integer page, Integer pageSize);
}
