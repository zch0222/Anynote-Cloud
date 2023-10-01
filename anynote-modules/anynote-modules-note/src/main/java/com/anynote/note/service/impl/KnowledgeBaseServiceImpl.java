package com.anynote.note.service.impl;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.common.security.utils.SecurityUtils;
import com.anynote.core.exception.user.UserParamException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.api.model.po.NoteKnowledgeBase;
import com.anynote.note.datascope.annotation.RequiresKnowledgeBasePermissions;
import com.anynote.note.datascope.aspect.KnowledgeBasePermissionsAspect;
import com.anynote.note.enums.KnowledgeBasePermissions;
import com.anynote.note.mapper.KnowledgeBaseMapper;
import com.anynote.note.model.bo.KnowledgeBaseQueryParam;
import com.anynote.note.model.dto.NoteKnowledgeBaseDTO;
import com.anynote.note.service.KnowledgeBaseService;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.po.SysUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 知识库服务
 * @author 称霸幼儿园
 */
@Service
public class KnowledgeBaseServiceImpl extends ServiceImpl<KnowledgeBaseMapper, NoteKnowledgeBase>
        implements KnowledgeBaseService {

    @Autowired
    private TokenUtil tokenUtil;


    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.READ, message = "没有获取该知识库权限")
    @Override
    public NoteKnowledgeBaseDTO getKnowledgeBaseById(KnowledgeBaseQueryParam queryParam) {
//        LoginUser loginUser = tokenUtil.getLoginUser();
//        KnowledgeBaseQueryParam queryParam = new KnowledgeBaseQueryParam();
//        queryParam.setId(id);
        NoteKnowledgeBaseDTO noteKnowledgeBaseDTO = this.baseMapper.selectKnowledgeBaseById(queryParam);
        if (StringUtils.isNull(noteKnowledgeBaseDTO)) {
            throw new UserParamException("获取知识库失败", ResCode.INVALID_USER_INPUT_NOT_FOUND);
        }
        noteKnowledgeBaseDTO.setPermissions(((Integer) queryParam.getParams()
                .get(KnowledgeBasePermissionsAspect.KNOWLEDGE_BASE_PERMISSIONS)));
        return noteKnowledgeBaseDTO;
    }

    @Override
    public PageBean<NoteKnowledgeBaseDTO> getUsersOrganizationKnowledgeBase(Integer page, Integer pageSize) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        PageHelper.startPage(page, pageSize,"update_time desc");
        List<NoteKnowledgeBaseDTO> noteKnowledgeBaseDTOList =
                this.selectOrganizationKnowledgeBasesByUserIdByPage(loginUser.getUserId());
        PageInfo<NoteKnowledgeBaseDTO> pageInfo = new PageInfo<>(noteKnowledgeBaseDTOList);
        PageBean pageBean = new PageBean();
        pageBean.setRows(noteKnowledgeBaseDTOList);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setPages(pageInfo.getPages());
        return pageBean;
    }

    @Override
    public List<NoteKnowledgeBaseDTO> selectOrganizationKnowledgeBasesByUserIdByPage(Long userId) {
        KnowledgeBaseQueryParam queryParam = new KnowledgeBaseQueryParam();
        queryParam.setStatus(0);
        return this.baseMapper.selectOrganizationKnowledgeBaseList(queryParam);
    }

    @Override
    public Integer getUserKnowledgeBasePermissions(Long userId, Long knowledgeBaseId) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        if (SysUser.isAdminX(loginUser.getSysUser().getRole())) {
            return 1;
        }
        return this.baseMapper.selectUserKnowledgeBasePermissions(userId, knowledgeBaseId);
    }

    @Override
    public Integer getUserKnowledgeBasePermissionsByNoteId(Long userId, Long noteId) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        if (SysUser.isAdminX(loginUser.getSysUser().getRole())) {
            return 1;
        }
        return this.baseMapper.selectUserKnowledgeBasePermissionsByNoteId(userId, noteId);
    }
}
