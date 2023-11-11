package com.anynote.note.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.api.model.po.NoteKnowledgeBase;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.dto.KnowledgeBaseImportUserVO;
import com.anynote.note.api.model.dto.NoteKnowledgeBaseDTO;
import com.anynote.system.api.model.vo.KnowledgeBaseUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 知识库服务
 * @author 称霸幼儿园
 */
public interface KnowledgeBaseService extends IService<NoteKnowledgeBase> {

    public Long createKnowledgeBase(KnowledgeBaseCreateParam createParam);

    public NoteKnowledgeBaseDTO getKnowledgeBaseById(KnowledgeBaseQueryParam queryParam);

    public PageBean<NoteKnowledgeBaseDTO> getUsersOrganizationKnowledgeBase(Integer page, Integer pageSize);

    /**
     * 获取用户知识库
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean<NoteKnowledgeBaseDTO> getUserKnowledgeBases(Integer page, Integer pageSize);

    /**
     * 获取用户所有知识库的id
     * @param userId 用户id
     * @return
     */
    public List<Long> getUsersKnowledgeBaseIds(Long userId);

    /**
     * 获取知识库中所有用户的id
     * @param knowledgeBaseId
     * @return
     */
    public List<Long> getAllKnowledgeBaseUserId(Long knowledgeBaseId);

    public List<Long> getAllKnowledgeBaseManagerId(Long knowledgeBaseId);

    public List<Long> getAllMemberKnowledgeBaseUserId(Long knowledgeBaseId);

    public PageBean<KnowledgeBaseUserVO> getKnowledgeBaseUsers(KnowledgeBaseUsersQueryParam queryParam);

    public List<NoteKnowledgeBaseDTO> selectOrganizationKnowledgeBasesByUserIdByPage(Long userId);

    public Integer getUserKnowledgeBasePermissions(Long userId, Long knowledgeBaseId);

    public Integer getUserKnowledgeBasePermissionsByNoteId(Long userId, Long noteId);

    public Long getKnowledgeBaseMemberCount(KnowledgeBaseQueryParam queryParam);

    public KnowledgeBaseImportUserVO importKnowledgeBaseUser(KnowledgeBaseImportUserParam importUserParam);


    public String updateKnowledgeBase(KnowledgeBaseUpdateParam updateParam);

    /**
     * 超级管理员获取知识库列表
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean<NoteKnowledgeBaseDTO> getManagerKnowledgeBaseList(KnowledgeBaseQueryParam queryParam);

}
