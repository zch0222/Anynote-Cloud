package com.anynote.note.service;

import com.anynote.note.api.model.dto.GetUserKnowledgeBaseListDTO;
import com.anynote.note.api.model.po.UserKnowledgeBase;
import com.anynote.note.model.bo.UserKnowledgeBaseParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserKnowledgeBaseService extends IService<UserKnowledgeBase> {

    public List<UserKnowledgeBase> getUserKnowledgeBaseList(GetUserKnowledgeBaseListDTO getUserKnowledgeBaseListDTO);

    public void updateUserKnowledgeBase(UserKnowledgeBaseParam userKnowledgeBaseParam);

    public void addUserKnowledgeBase(UserKnowledgeBaseParam userKnowledgeBaseParam);

    public void deleteUserKnowledgeBase(UserKnowledgeBaseParam userKnowledgeBaseParam);
}
