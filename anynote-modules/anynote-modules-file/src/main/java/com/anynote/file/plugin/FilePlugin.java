package com.anynote.file.plugin;

import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.api.model.bo.OSSSignature;
import com.anynote.file.api.model.bo.OssSliceUploadTaskInfo;
import com.anynote.file.enums.OssTypeEnum;
import com.anynote.file.api.model.bo.ObjectURL;
import com.anynote.file.model.bo.OssObjectComposeResponse;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 文件插件
 * @author 称霸幼儿园
 */
public interface FilePlugin {

    public OssTypeEnum getPluginOssType();

    public String multipartFileUpload(CommonsMultipartFile file, String path, String fileName);

    public OSSSignature getOssSignature(Integer durationSeconds, String objectName);

    /**
     * 分片上传
     * @param file 文件
     * @param objectName 对象名称
     * @param hash 文件hash
     * @return 文件信息
     */
    public FileDTO sliceUpload(MultipartFile file, String hash, String objectName);

    /**
     * 创建对象存储分片上传任务
     * @param hash 文件哈希
     * @param fileSize 文件大小
     * @param path 文件路径
     * @param objectName 对象名称
     * @param uploadId 上传id
     * @return 创建对象存储上传任务
     */
    public OssSliceUploadTaskInfo createOssSliceUploadTask(String hash, Double fileSize, String path,
                                                           String objectName, String uploadId);


    public boolean exist(String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * 对象存储分片合并
     * @param objectNameList 分片列表（必须按照分片顺序）
     * @param targetObjectName 目标对象位置
     * @return 对象名称
     */
    public OssObjectComposeResponse composeOssSliceUploadObject(List<String> objectNameList, String targetObjectName);

    /**
     * 获取对象URL
     * @param objectName 对象名称
     * @param durationSeconds 过期时间(秒)
     * @return 对象URL信息
     */
    public ObjectURL getObjectUrl(String objectName, Integer durationSeconds);
}
