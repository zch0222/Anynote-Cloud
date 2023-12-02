package com.anynote.note.service.impl;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.constant.FileConstants;
import com.anynote.core.utils.RemoteResDataUtil;
import com.anynote.core.web.model.bo.CreateResEntity;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.RemoteFileService;
import com.anynote.file.api.enums.FileSources;
import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.api.model.po.FilePO;
import com.anynote.note.api.model.po.Doc;
import com.anynote.note.datascope.annotation.RequiresKnowledgeBasePermissions;
import com.anynote.note.enums.DocType;
import com.anynote.note.enums.KnowledgeBasePermissions;
import com.anynote.note.mapper.DocMapper;
import com.anynote.note.model.bo.PDFCreateParam;
import com.anynote.note.service.DocService;
import com.anynote.system.api.model.bo.LoginUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 文档服务器 IMPL
 * @author 称霸幼儿园
 */
@Service
public class DocServiceImpl extends ServiceImpl<DocMapper, Doc>
        implements DocService {

    @Resource
    private RemoteFileService remoteFileService;

    @Resource
    private TokenUtil tokenUtil;

    @Override
    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.MANAGE, message = "您没有权限上传PDF文档")
    public CreateResEntity createPDF(PDFCreateParam pdfCreateParam) {
        Date date = new Date();
        LoginUser loginUser = tokenUtil.getLoginUser();
        ResData<FilePO> resData = remoteFileService.uploadFile(pdfCreateParam.getPdf(),
                FileConstants.DOC_PDF, loginUser.getSysUser().getId(), pdfCreateParam.getUploadId(), FileSources.KNOWLEDGE_BASE_DOC.getValue());

        FilePO filePO = RemoteResDataUtil.getResData(resData, "上传文档失败");

        Doc doc = Doc.builder()
                .fileId(filePO.getId())
                .name(filePO.getOriginalFileName())
                .knowledgeBaseId(pdfCreateParam.getKnowledgeBaseId())
                .type(DocType.PDF.getValue())
                .dataScope(1)
                .permissions("70000")
                .deleted(0)
                .createBy(loginUser.getSysUser().getId())
                .createTime(date)
                .updateBy(loginUser.getSysUser().getId())
                .updateTime(date)
                .build();
        this.baseMapper.insert(doc);

        return CreateResEntity.builder()
                .id(doc.getId())
                .build();
    }
}
