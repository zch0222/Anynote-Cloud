package com.anynote.note.service.impl;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.constant.Constants;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.exception.auth.AuthException;
import com.anynote.core.exception.user.UserParamException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.RemoteFileService;
import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.api.model.bo.FileUploadParam;
import com.anynote.note.api.model.po.Note;
import com.anynote.note.api.model.po.NoteImage;
import com.anynote.note.api.model.po.NoteText;
import com.anynote.note.datascope.annotation.RequiresKnowledgeBasePermissions;
import com.anynote.note.datascope.annotation.RequiresNotePermissions;
import com.anynote.note.datascope.aspect.RequiresNotePermissionsAspect;
import com.anynote.note.enums.KnowledgeBasePermissions;
import com.anynote.note.enums.NotePermissions;
import com.anynote.note.mapper.NoteMapper;
import com.anynote.note.mapper.NoteTextMapper;
import com.anynote.note.model.bo.*;
import com.anynote.note.service.KnowledgeBaseService;
import com.anynote.note.service.NoteImageService;
import com.anynote.note.service.NoteService;
import com.anynote.note.utils.MarkdownUtil;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.po.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 笔记服务实现
 * @author 称霸幼儿园
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note>
        implements NoteService {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @Autowired
    private NoteImageService noteImageService;

    @Autowired
    private NoteTextMapper noteTextMapper;

    @Autowired
    private RemoteFileService remoteFileService;

    @Override
    public PageBean<Note> getNotesByKnowledgeBaseId(NoteQueryParam queryParam) {

        List<Note> noteList = this.baseMapper.selectNoteInfoList(queryParam);
        PageInfo<Note> pageInfo = new PageInfo<>(noteList);
        PageBean<Note> pageBean = new PageBean<>();
        pageBean.setRows(noteList);
        pageBean.setTotal(pageInfo.getTotal());
        pageBean.setPages(pageInfo.getPages());
        return pageBean;
    }

    @Override
    @RequiresNotePermissions(NotePermissions.READ)
    public Note getNoteById(NoteQueryParam queryParam) {
        Note note = this.baseMapper.selectNoteById(queryParam);
        if (StringUtils.isNull(note)) {
            throw new UserParamException("访问笔记失败", ResCode.USER_REQUEST_PARAM_ERROR);
        }
        note.setNotePermissions(((NotePermissions) queryParam.getParams()
                .get(RequiresNotePermissionsAspect.NOTE_PERMISSIONS)).getValue());
        return note;
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.EDIT,
            message = "没有权限创建笔记")
    @Override
    public Long createNote(NoteCreateParam createParam) {
        Date date = new Date();
        LoginUser loginUser = tokenUtil.getLoginUser();

        NoteText noteText = new NoteText();
        noteText.setContent("# " + createParam.getTitle());
        noteText.setCreateBy(loginUser.getSysUser().getId());
        noteText.setCreateTime(date);
        noteText.setUpdateBy(loginUser.getSysUser().getId());
        noteText.setUpdateTime(date);
        noteTextMapper.insert(noteText);

        Note note = Note.builder()
                .title(createParam.getTitle())
                .noteTextId(noteText.getId())
                .status(0)
                .dataScope(1)
                .permissions("70000")
                .deleted(0)
                .knowledgeBaseId(createParam.getKnowledgeBaseId())
                .build();
        note.setCreateBy(loginUser.getSysUser().getId());
        note.setCreateTime(date);
        note.setUpdateBy(loginUser.getSysUser().getId());
        note.setCreateTime(date);

        this.baseMapper.insert(note);
        return note.getId();
    }

    /**
     * 提交笔记
     * 已经鉴权过无需再次鉴权
     * @param noteId
     */
    @Override
    public Integer submitNote(Long noteId) {
        Note note = Note.builder()
                .id(noteId)
                .permissions("44000")
                .build();
        return this.baseMapper.updateById(note);
    }

    /**
     * 删除笔记
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequiresNotePermissions(NotePermissions.MANAGE)
    @Override
    public String deleteNote(NoteDeleteParam param) {
        LambdaQueryWrapper<Note> noteLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noteLambdaQueryWrapper
                .eq(Note::getId, param.getId())
                .select(Note::getNoteTextId);
        Note noteInfo = this.baseMapper.selectOne(noteLambdaQueryWrapper);
        this.baseMapper.deleteById(param.getId());
        noteTextMapper.deleteById(noteInfo.getNoteTextId());
        return Constants.SUCCESS_RES;
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresNotePermissions(NotePermissions.EDIT)
    @Override
    public String editNote(NoteUpdateParam updateParam) {
        Integer count = this.baseMapper.updateNote(updateParam);
        if (count != 1) {
            throw new BusinessException("更新笔记失败", ResCode.USER_ERROR);
        }

        LambdaQueryWrapper<Note> noteLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noteLambdaQueryWrapper
                .eq(Note::getId, updateParam.getId())
                .select(Note::getNoteTextId);
        Note noteInfo = this.baseMapper.selectOne(noteLambdaQueryWrapper);
        updateParam.setContentId(noteInfo.getNoteTextId());
        Integer contentCount = this.baseMapper.updateContent(updateParam);
        if (contentCount != 1) {
            throw new BusinessException("更新笔记失败", ResCode.USER_ERROR);
        }
        return Constants.SUCCESS_RES;
    }

    @RequiresNotePermissions(NotePermissions.EDIT)
    @Override
    public MarkdownImage uploadNoteImage(NoteImageUploadParam uploadParam) {
        LoginUser loginUser = tokenUtil.getLoginUser();
//        FileUploadParam fileUploadParam = FileUploadParam.builder()
//                .file(uploadParam.getImage())
//                .path(StringUtils.format("note/{}", uploadParam.getId()))
//                .build();
        ResData<FileDTO> fileDTOResData = remoteFileService.uploadFile(uploadParam.getImage(),
                StringUtils.format("note/{}", uploadParam.getId()));
        if (StringUtils.isNull(fileDTOResData) || StringUtils.isNull(fileDTOResData.getData())) {
            throw new BusinessException("图片保存失败", ResCode.INNER_FILE_SERVICE_ERROR);
        }

        if (!ResData.SUCCESS.equals(fileDTOResData.getCode())) {
            throw new BusinessException("图片保存失败", ResCode.INNER_FILE_SERVICE_ERROR);
        }
        FileDTO fileDTO = fileDTOResData.getData();

        NoteImage noteImage = NoteImage.builder()
                .originalFileName(fileDTO.getOriginalFileName())
                .fileName(fileDTO.getFileName())
                .url(fileDTO.getUrl())
                .userId(loginUser.getSysUser().getId())
                .build();
        noteImageService.getBaseMapper().insert(noteImage);
        return MarkdownImage.builder()
                .image(MarkdownUtil.buildMarkdownImage(noteImage.getOriginalFileName(),
                        noteImage.getUrl()))
                .build();
    }

    @Override
    public NotePermissions getNotePermissions(Long noteId) {
        LambdaQueryWrapper<Note> noteLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noteLambdaQueryWrapper
                .eq(Note::getId, noteId)
                .select(Note::getDataScope, Note::getCreateBy, Note::getPermissions);
        Note noteInfo = this.baseMapper.selectOne(noteLambdaQueryWrapper);
        if (StringUtils.isNull(noteInfo)) {
            throw new UserParamException("笔记不存在", ResCode.INVALID_USER_INPUT_NOT_FOUND);
        }
        LoginUser loginUser = tokenUtil.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();

        Integer knowledgeBasePermissions = knowledgeBaseService.getUserKnowledgeBasePermissionsByNoteId(sysUser.getId(), noteId);
        // 笔记所有者
        if (noteInfo.getCreateBy().equals(sysUser.getId())) {
            return this.permissionCompute(Integer.parseInt(noteInfo.getPermissions().substring(0, 1)));
        }
        // 非知识库成员
        if (StringUtils.isNull(knowledgeBasePermissions)) {
            return this.permissionCompute(Integer.parseInt(noteInfo.getPermissions().substring(3, 4)));
        }
        // 知识库管理员
        else if (KnowledgeBasePermissions.MANAGE.getValue() == knowledgeBasePermissions) {
            return this.permissionCompute(Integer.parseInt(noteInfo.getPermissions().substring(1, 2)));
        }
        // 知识库中有编辑权限的用户
        else if (KnowledgeBasePermissions.EDIT.getValue() == knowledgeBasePermissions) {
            return this.permissionCompute(Integer.parseInt(noteInfo.getPermissions().substring(2, 3)));
        }
        // 知识库中有阅读权限的用户
        else if (KnowledgeBasePermissions.READ.getValue() == knowledgeBasePermissions) {
            NotePermissions notePermissions = this.permissionCompute(Integer.valueOf(noteInfo.getPermissions().charAt(2)));
            if (NotePermissions.NO.getValue() < notePermissions.getValue()) {
                return NotePermissions.READ;
            }
        }
        return NotePermissions.NO;
    }

    private NotePermissions permissionCompute(Integer permission) {
        if (0 == permission) {
            return NotePermissions.NO;
        }
        else if (4 == permission) {
            return NotePermissions.READ;
        }
        else if (6 == permission) {
            return NotePermissions.EDIT;
        }
        else if (7 == permission) {
            return NotePermissions.MANAGE;
        }
        else {
            throw new BusinessException("笔记权限错误", ResCode.AUTH_ERROR);
        }
    }

    @Override
    public Integer getNoteDataScope(Long noteId) {
        LambdaQueryWrapper<Note> noteLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noteLambdaQueryWrapper
                .eq(Note::getId, noteId)
                .select(Note::getDataScope);
        Note noteInfo = this.baseMapper.selectOne(noteLambdaQueryWrapper);
        return noteInfo.getDataScope();
    }
}
