package com.anynote.file.controller;

import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.model.bo.*;
import com.anynote.file.api.model.dto.*;
import com.anynote.file.api.model.po.FilePO;
import com.anynote.file.api.model.vo.*;
import com.anynote.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 文件Controller
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("")
@Validated
public class FileController {

    @Autowired
    private FileService fileService;

    @InnerAuth
    @PostMapping
    public ResData<FilePO> uploadFile(@RequestParam("file") @NotNull(message = "文件不能为空") CommonsMultipartFile file,
                                      @RequestParam("path") @NotNull(message = "文件路径不能为空") String path,
                                      @RequestParam("userId") @NotNull(message = "用户ID不能为空") Long userId,
                                      @RequestParam("uploadId") @NotNull(message = "上传ID不能为空") String uploadId,
                                      @RequestParam("source") @NotNull(message = "文件来源不能为空") Integer source) {
        return ResUtil.success(fileService.upload(file, path, userId, uploadId, source));
    }


    @InnerAuth
    @GetMapping("progress/{uploadId}")
    public ResData<UploadProgress> getFileUploadProgress(@PathVariable("uploadId") String uploadId) {
        return ResUtil.success(fileService.getFileUploadProgress(uploadId));
    }

    @InnerAuth
    @PostMapping("createHuaweiOBSTemporarySignature")
    public ResData<HuaweiOBSTemporarySignature> createHuaweiOBSTemporarySignature(
            @RequestBody @Validated CreateHuaweiOBSTemporarySignatureDTO createHuaweiOBSTemporarySignatureDTO) {
        return ResUtil.success(fileService.createHuaweiOBSTemporarySignature(createHuaweiOBSTemporarySignatureDTO.getPath(),
                createHuaweiOBSTemporarySignatureDTO.getFileName(), createHuaweiOBSTemporarySignatureDTO.getExpireSeconds(),
                createHuaweiOBSTemporarySignatureDTO.getContentType(),
                createHuaweiOBSTemporarySignatureDTO.getSource()));
    }

    @InnerAuth
    @PostMapping("completeHuaweiOBSUpload")
    public ResData<FilePO> completeHuaweiOBSUpload(
            @RequestBody @Validated CompleteUploadDTO completeUploadDTO) {
        return ResUtil.success(fileService.completeUpload(completeUploadDTO));
    }

    @GetMapping("/{id}")
    @InnerAuth
    public ResData<FilePO> getFileById(@PathVariable("id") Long id) {
        return ResUtil.success(fileService.getFileById(id));
    }


    /**
     * 创建OSS分片上传任务
     * @param ossSliceUploadTaskCreateDTO
     * @return
     */
    @InnerAuth
    @PostMapping("/ossSliceUploadTasks")
    public ResData<OssSliceUploadTaskVO> createOssSliceUploadTask(@RequestBody @Validated
                                                                      OssSliceUploadTaskCreateDTO ossSliceUploadTaskCreateDTO) {
        return ResUtil.success(fileService.createOssSliceUploadTask(ossSliceUploadTaskCreateDTO.getPath(),
                ossSliceUploadTaskCreateDTO.getFileName(), ossSliceUploadTaskCreateDTO.getHash(),
                ossSliceUploadTaskCreateDTO.getFileSize(), ossSliceUploadTaskCreateDTO.getContentType(),
                ossSliceUploadTaskCreateDTO.getSource()));
    }

    /**
     * 获取上传分片签名
     * @return 分片签名
     */
    @PostMapping("/getOssSliceUploadSignatures")
    public ResData<OssSliceUploadSignatureVO> getOssSliceUploadSignature(@RequestBody @Validated OssSliceUploadSignatureDTO
                                                                                 ossSliceUploadSignatureDTO) {
        return ResUtil.success(fileService.getOssSliceUploadSignature(ossSliceUploadSignatureDTO.getUploadId(),
                ossSliceUploadSignatureDTO.getChunkIndexList()));
    }

    /**
     * 标记已经上传的分片
     */
    @PostMapping("/markOssSliceUploadSignatures")
    public ResData<OssSliceUploadChunkMarkVO> markOssUploadSlice(@RequestBody @Validated OssSliceUploadSignatureDTO
                                                                             ossSliceUploadSignatureDTO) {
        return ResUtil.success(fileService.markOssUploadSlice(ossSliceUploadSignatureDTO.getUploadId(), ossSliceUploadSignatureDTO.getChunkIndexList()));
    }

    /**
     * 获取分片任务状态
     * @param uploadId 上传任务id
     * @return 分片任务状态
     */
    @GetMapping("ossSliceUploadTask/{uploadId}")
    public ResData<OssSliceUploadTaskVO> getOssSliceUploadTaskInfo(@PathVariable("uploadId") String uploadId) {
        return ResUtil.success(fileService.getOssSliceUploadTaskInfo(uploadId));
    }

    /**
     * 合并分片
     * @return
     */
    @PostMapping("/composeOssSliceUploadObject")
    public ResData<OssSliceUploadComposeOV> ossSliceUploadComposeObject(@RequestBody @Validated OssSliceUploadComposeDTO composeDTO) {
        return ResUtil.success(fileService.ossSliceUploadComposeObject(composeDTO.getUploadId()));
    }

    /**
     * 公共的获取文件接口
     * @param objectName 对象名称
     * @return 文件信息(七天)
     */
    @GetMapping("public/byObjectName")
    public ResData<ObjectURL> getObjectUrlByObjectName(@NotNull(message = "对象名称不能为空") String objectName) {
        return ResUtil.success(fileService.getObjectUrlByObjectName(objectName));
    }

    /**
     * 下载Object到服务器本地路径
     * @param downloadObjectDTO
     * @return
     */
    @PostMapping("downloadObject")
    @InnerAuth
    public ResData<String> downloadObject(@RequestBody @Validated DownloadObjectDTO downloadObjectDTO) {
        return ResUtil.success(fileService.downloadObject(downloadObjectDTO));
    }

    /**
     * 读取文本文件
     * @param objectName 对象名称
     * @return
     */
    @GetMapping("readTextFile")
    @InnerAuth
    public ResData<String> readTextFile(@NotNull(message = "对象名称不能为空") String objectName) {
        return ResUtil.success(fileService.readTextFile(objectName));
    }


}
