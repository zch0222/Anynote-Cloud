package com.anynote.note.service.impl;

import com.anynote.core.exception.BusinessException;
import com.anynote.note.api.enums.KnowledgeBasePermissions;
import com.anynote.note.api.model.dto.GetUserKnowledgeBaseListDTO;
import com.anynote.note.api.model.po.UserKnowledgeBase;
import com.anynote.note.datascope.annotation.RequiresKnowledgeBasePermissions;
import com.anynote.note.mapper.UserKnowledgeBaseMapper;
import com.anynote.note.model.bo.UserKnowledgeBaseParam;
import com.anynote.note.service.UserKnowledgeBaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserKnowledgeBaseServiceImpl extends ServiceImpl<UserKnowledgeBaseMapper, UserKnowledgeBase>
        implements UserKnowledgeBaseService {


    @Override
    public List<UserKnowledgeBase> getUserKnowledgeBaseList(GetUserKnowledgeBaseListDTO getUserKnowledgeBaseListDTO) {
        if (getUserKnowledgeBaseListDTO.getKnowledgeBaseIds().isEmpty()) {
            return Collections.emptyList();
        }
        return this.list(new LambdaQueryWrapper<UserKnowledgeBase>()
                .eq(UserKnowledgeBase::getUserId, getUserKnowledgeBaseListDTO.getUserId())
                .in(UserKnowledgeBase::getKnowledgeBaseId, getUserKnowledgeBaseListDTO.getKnowledgeBaseIds()));
    }

    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.MANAGE, message = "无法修改用户权限")
    @Override
    public void updateUserKnowledgeBase(UserKnowledgeBaseParam userKnowledgeBaseParam) {
        boolean res = this.update(new LambdaUpdateWrapper<UserKnowledgeBase>()
                .eq(UserKnowledgeBase::getUserId, userKnowledgeBaseParam.getUserId())
                .eq(UserKnowledgeBase::getKnowledgeBaseId, userKnowledgeBaseParam.getKnowledgeBaseId())
                .setEntity(UserKnowledgeBase.builder()
                        .permissions(userKnowledgeBaseParam.getPermissions())
                        .build()));
        if (!res) {
            throw new BusinessException("修改权限失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.MANAGE, message = "无法添加用户")
    @Override
    public void addUserKnowledgeBase(UserKnowledgeBaseParam userKnowledgeBaseParam) {
        try {
            int count = this.getBaseMapper().insert(UserKnowledgeBase.builder()
                    .knowledgeBaseId(userKnowledgeBaseParam.getKnowledgeBaseId())
                    .userId(userKnowledgeBaseParam.getUserId())
                    .permissions(userKnowledgeBaseParam.getPermissions())
                    .build());
            if (count != 1) {
                throw new BusinessException("添加用户失败");
            }
        } catch (DuplicateKeyException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("用户已经存在");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("添加用户失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.MANAGE, message = "无法移除用户")
    @Override
    public void deleteUserKnowledgeBase(UserKnowledgeBaseParam userKnowledgeBaseParam) {
        int count = this.baseMapper.delete(new LambdaQueryWrapper<UserKnowledgeBase>()
                .eq(UserKnowledgeBase::getUserId, userKnowledgeBaseParam.getUserId()));
    }



}
