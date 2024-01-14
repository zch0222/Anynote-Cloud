package com.anynote.file.service;

import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.api.model.bo.HuaweiOBSTemporarySignature;
import com.anynote.file.api.model.bo.UploadProgress;
import com.anynote.file.api.model.po.FilePO;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 文件上传
 * @author 称霸幼儿园
 */
public interface FileService extends IService<FilePO> {


    public FilePO upload(CommonsMultipartFile file, String path, Long userId, String uploadId, Integer source);


    public UploadProgress getFileUploadProgress(String uploadId);

    public HuaweiOBSTemporarySignature createHuaweiOBSTemporarySignature(String path, String fileName, Long expireSeconds);

}
