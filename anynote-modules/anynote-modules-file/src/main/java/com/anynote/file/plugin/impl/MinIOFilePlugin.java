package com.anynote.file.plugin.impl;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.StringUtils;
import com.anynote.file.api.enums.OSSSignatureType;
import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.api.model.bo.MinIOSignatureData;
import com.anynote.file.api.model.bo.OSSSignature;
import com.anynote.file.api.model.bo.OssSliceUploadTaskInfo;
import com.anynote.file.enums.OssTypeEnum;
import com.anynote.file.model.bo.MinIOConfig;
import com.anynote.file.model.bo.OssObjectComposeResponse;
import com.anynote.file.plugin.FilePlugin;
import io.minio.*;
import io.minio.credentials.AssumeRoleProvider;
import io.minio.credentials.Credentials;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class MinIOFilePlugin implements FilePlugin {

    private final MinIOConfig minIOConfig;

    private final MinioClient minioClient;



    public MinIOFilePlugin(MinIOConfig config) {
        minIOConfig = config;
        minioClient = new MinioClient.Builder()
                .endpoint(config.getEndPoint())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
    }

    @Override
    public OssTypeEnum getPluginOssType() {
        return OssTypeEnum.MIN_IO;
    }

    @Override
    public String multipartFileUpload(CommonsMultipartFile file, String path, String fileName) {
        return "";
    }

    @Override
    public OSSSignature getOssSignature(Integer durationSeconds, String objectName) {
        String preSignedObjectUrl = this.getPreSignedObjectUrl(objectName, durationSeconds);
        return OSSSignature.builder()
                .type(OSSSignatureType.MIN_IO)
                .credentials(MinIOSignatureData.builder()
                        .url(preSignedObjectUrl)
                        .build())
                .build();
    }

    /**
     * 获取MinIO PreSignedObject Url
     * @param objectName 对象名称
     * @param durationSeconds 过期时间（秒为单位）
     * @return 预签名URL
     */
    private String getPreSignedObjectUrl(String objectName, Integer durationSeconds) {
        try {
            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.PUT)
                    .bucket(this.minIOConfig.getBucketName())
                    .object(StringUtils.format("{}/{}", this.minIOConfig.getBasePath(), objectName))
                    .expiry(durationSeconds, TimeUnit.SECONDS)
                    .build());
            log.info("PreSignedObjectUrl: {}", url);
            return url;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("获取上传签名失败");
        }

    }

    private String getPolicy(String bucketsName, String objectName) {

        String policy = "{\n" +
                " \"Version\": \"2012-10-17\",\n" +
                " \"Statement\": [\n" +
                "  {\n" +
                "   \"Effect\": \"Allow\",\n" +
                "   \"Action\": [\n" +
                "    \"s3:ListBucket\"\n" +
                "   ],\n" +
                "   \"Resource\": [\n" +
                "    \"arn:aws:s3:::companybucket\"\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"Sid\": \"AllowUserToReadWriteObjectData\",\n" +
                "   \"Effect\": \"Allow\",\n" +
                "   \"Action\": [\n" +
                "    \"s3:GetObject\",\n" +
                "    \"s3:PutObject\"\n" +
                "   ],\n" +
                "   \"Resource\": [\n" +
                "    \"arn:aws:s3:::companybucket/Development/*\"\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"Sid\": \"ExplicitlyDenyAnyRequestsForAllOtherFoldersExceptDevelopment\",\n" +
                "   \"Effect\": \"Deny\",\n" +
                "   \"Action\": [\n" +
                "    \"s3:ListBucket\"\n" +
                "   ],\n" +
                "   \"Resource\": [\n" +
                "    \"arn:aws:s3:::companybucket\"\n" +
                "   ],\n" +
                "   \"Condition\": {\n" +
                "    \"Null\": {\n" +
                "     \"s3:prefix\": [\n" +
                "      false\n" +
                "     ]\n" +
                "    },\n" +
                "    \"StringNotLike\": {\n" +
                "     \"s3:prefix\": [\n" +
                "      \"Development/*\",\n" +
                "      \"\"\n" +
                "     ]\n" +
                "    }\n" +
                "   }\n" +
                "  }\n" +
                " ]\n" +
                "}";
//        String policy =
//                "{\n" +
//                " \"Version\": \"2012-10-17\",\n" +
//                " \"Statement\": [\n" +
//                "  {\n" +
//                "   \"Effect\": \"Allow\",\n" +
//                "   \"Action\": [\n" +
//                "    \"s3:GetObject\",\n" +
//                //"    \"s3:GetBucketLocation\",\n" +
//                "    \"s3:PutObject\"\n" +
//                "   ],\n" +
//                "   \"Resource\": [\n" +
//                        "\"arn:aws:s3:::anynote/Development/*\"\n" +
//                //"    \"arn:aws:s3:::" + bucketsName + "/userA/*\"\n" +
//                "   ]\n" +
////                "   \"Condition\": {\"StringEquals\": {\"s3:ExistingObjectTag/environment\": \"${aws:username}\"}}\n"+
//                "  }\n" +
//                " ]\n" +
//                "}";
//        String policy = "{\n" +
//                "    \"Version\": \"2012-10-17\",\n" +
//                "    \"Statement\": [\n" +
//                "        {\n" +
//                "            \"Effect\": \"Allow\",\n" +
//                "            \"Action\": [\"s3:GetObject\", \"s3:GetBucketLocation\"],\n" +
//                "            \"Resource\": [\"arn:aws:s3:::anynote/*\"]\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"Effect\": \"Allow\",\n" +
//                "            \"Action\": [\"s3:PutObject\"],\n" +
//                "            \"Resource\": [\"arn:aws:s3:::anynote/*\"],\n" +
//                "            \"Condition\": {\"StringEquals\": {\"s3:x-amz-meta-uploader\": \"${aws:username}\"}}\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
        log.info("minio sts policy :\n {}", policy);
        return policy;
    }

    private Credentials getMinIOCredentials(Integer durationSeconds, String objectName) {
        AssumeRoleProvider provider = null;
        try {
             provider = new AssumeRoleProvider(
                    this.minIOConfig.getEndPoint(),
                    this.minIOConfig.getAccessKey(),
                    this.minIOConfig.getSecretKey(),
                    durationSeconds,
//                     "{\n" +
//                             " \"Version\": \"2012-10-17\",\n" +
//                             " \"Statement\": [\n" +
//                             "  {\n" +
//                             "   \"Effect\": \"Allow\",\n" +
//                             "   \"Action\": [\n" +
//                             "    \"s3:GetObject\",\n" +
//                             "    \"s3:GetBucketLocation\",\n" +
//                             "    \"s3:PutObject\"\n" +
//                             "   ],\n" +
//                             "   \"Resource\": [\n" +
//                             "    \"arn:aws:s3:::" + objectName + "\"\n" +
//                             "   ]\n" +
//                             "  }\n" +
//                             " ]\n" +
//                             "}",
                     getPolicy(this.minIOConfig.getBucketName() ,objectName),
                    null,
                    "testAnynoteUser",
                    "testAnynoteUser",
                    null,
                    null
            );
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("获取文件临时token失败");
        }
        Credentials credentials = provider.fetch();
        System.out.println(credentials.accessKey());
        System.out.println(credentials.secretKey());
        System.out.println(credentials.sessionToken());
        System.out.println(credentials.isExpired());
        return credentials;
    }

    @Override
    public FileDTO sliceUpload(MultipartFile file, String hash, String objectName) {

        return null;
    }

    @Override
    public OssSliceUploadTaskInfo createOssSliceUploadTask(String hash, Double fileSize, String path,
                                                           String objectName, String uploadId) {
        // 单位是MB
        int chunkSize = Math.max(5, (int)Math.ceil(fileSize / 1000));
        // 分片数量
        int chunkCount = (int) Math.ceil((double) fileSize / chunkSize);
        return OssSliceUploadTaskInfo.builder()
                .totalChunk(chunkCount)
                .chunkSize(chunkSize)
                .chunkFolder(StringUtils.format("{}/{}/{}", this.minIOConfig.getBasePath(), path, uploadId))
                .objectName(objectName)
                .build();
    }

    @Override
    public boolean exist(String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.statObject(StatObjectArgs.builder()
                .bucket(this.minIOConfig.getBucketName())
                .object(objectName)
                .build()) != null;
    }

    @Override
    public OssObjectComposeResponse composeOssSliceUploadObject(List<String> objectNameList, String targetObjectName) {
        List<ComposeSource> sources = objectNameList.stream()
                .map(objectName -> ComposeSource.builder()
                        .bucket(this.minIOConfig.getBucketName())
                        .object(objectName)
                        .build())
                .collect(Collectors.toList());
        try {
            ObjectWriteResponse objectWriteResponse = this.minioClient.composeObject(ComposeObjectArgs.builder()
                    .bucket(this.minIOConfig.getBucketName())
                    .object(targetObjectName)
                    .sources(sources)
                    .build());
            return OssObjectComposeResponse.builder()
                    .hash(objectWriteResponse.etag())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(StringUtils.format("合并文件：\"{}\"失败"));
        }
    }
}
