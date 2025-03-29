package com.anynote.file.service;

import com.anynote.file.api.model.bo.HuaweiOBSTemporarySignature;
import com.anynote.file.api.model.bo.ObjectURL;
import com.anynote.file.api.model.bo.UploadProgress;
import com.anynote.file.api.model.dto.CompleteUploadDTO;
import com.anynote.file.api.model.dto.DownloadObjectDTO;
import com.anynote.file.api.model.po.FilePO;
import com.anynote.file.api.model.vo.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

/**
 * 文件上传
 * @author 称霸幼儿园
 */
public interface FileService extends IService<FilePO> {


    public FilePO upload(CommonsMultipartFile file, String path, Long userId, String uploadId, Integer source);


    public UploadProgress getFileUploadProgress(String uploadId);

    public HuaweiOBSTemporarySignature createHuaweiOBSTemporarySignature(String path, String fileName,
                                                                         Long expireSeconds, String contentType,
                                                                         Integer source);

    /**
     * 完成上传回调
     * @param completeUploadDTO 完成上传DTO
     * @return file ID
     */
    public FilePO completeUpload(CompleteUploadDTO completeUploadDTO);

    public FilePO getFileById(Long id);

    /**
     * 创建一个分片任务
     * @param path 文件路径
     * @param fileName 文件名称
     * @param hash 文件哈希
     * @param fileSize 文件大小
     * @param contentType contentType
     * @param source 文件来源
     * @return 文件任务信息
     */
    public OssSliceUploadTaskVO createOssSliceUploadTask(String path, String fileName,
                                                         String hash, Double fileSize, String contentType,
                                                         Integer source);

    /**
     * 获取上传分片的签名
     * @param uploadId 文件上传id
     * @param chunkIndexList 分片id列表
     * @return 分片签名
     */
    public OssSliceUploadSignatureVO getOssSliceUploadSignature(String uploadId, Set<Integer> chunkIndexList);


    /**
     * 标记上传完成的分片
     * @param uploadId 上传的id
     * @param chunkIndexList 完成的分片id列表
     * @return
     */
    public OssSliceUploadChunkMarkVO markOssUploadSlice(String uploadId, Set<Integer> chunkIndexList);

    /**
     * 获取上传任务信息
     * @param uploadId 上传id
     * @return 上传任务信息
     */
    public OssSliceUploadTaskVO getOssSliceUploadTaskInfo(String uploadId);


    /**
     * 对象存储合并上传的分片
     * @param uploadId 文件上传id
     * @return
     */
    public OssSliceUploadComposeOV ossSliceUploadComposeObject(String uploadId);


    /**
     * 公共的获取文件信息接口
     * @param objectName 对象名称
     * @return 文件信息
     */
    public ObjectURL getObjectUrlByObjectName(String objectName);

    /**
     * 下载对象到本地目录
     * @param downloadObjectDTO
     * @return
     */
    public String downloadObject(DownloadObjectDTO downloadObjectDTO);

    /**
     * 上传文件
     * @param inputStream
     * @param size
     * @param objectName
     * @return objectName
     */
    public String upload(ByteArrayInputStream inputStream, long size, String objectName);

    /**
     * 读取文本文件
     * @param objectName 对象名称
     * @return 文本
     */
    public String readTextFile(String objectName);
}
