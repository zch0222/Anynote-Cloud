package com.anynote.file.plugin.impl;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.file.api.model.bo.HuaweiOBSTemporarySignature;
import com.anynote.file.enums.OssTypeEnum;
import com.anynote.file.model.bo.HuaweiOBSConfig;
import com.anynote.file.plugin.FilePlugin;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 华为云文件插件
 * @author 称霸幼儿园
 */
@Slf4j
public class HuaweiFilePlugin implements FilePlugin {

    /**
     * 配置
     */
    private HuaweiOBSConfig huaweiOBSConfig;

    public HuaweiFilePlugin(HuaweiOBSConfig huaweiOBSConfig) {
        this.huaweiOBSConfig = huaweiOBSConfig;
    }

    @Override
    public OssTypeEnum getPluginOssType() {
        return OssTypeEnum.HUAWEI_OBS;
    }

    @Override
    public String multipartFileUpload(CommonsMultipartFile file, String path, String fileName) {
        ObsClient obsClient = null;
        try {
            obsClient = this.buildObsClient();
            PutObjectResult putObjectResult = obsClient.putObject(huaweiOBSConfig.getBucketName(),
                    huaweiOBSConfig.getBasePath() + "/" + path + "/" + fileName,
                    file.getInputStream());
            return putObjectResult.getObjectUrl();
        } catch (ObsException e) {
            e.printStackTrace();
            log.error("Huawei OBS Upload HTTP Code: " + e.getResponseCode());
            log.error("Huawei OBS Upload Error Code:" + e.getErrorCode());
            log.error("Huawei OBS Upload Error Message: " + e.getErrorMessage());

            log.error("Huawei OBS Upload Request ID: " + e.getErrorRequestId());
            log.error("Huawei OBS Upload Host ID: " + e.getErrorHostId());
            throw new BusinessException("文件上传失败", ResCode.HUAWEI_OBS_UPLOAD_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("文件上传失败", ResCode.HUAWEI_OBS_UPLOAD_ERROR);
        } finally {
            if (StringUtils.isNotNull(obsClient)) {
                try {
                    obsClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HuaweiOBSTemporarySignature createTemporarySignature(String path, String fileName, Long expireSeconds) {
        ObsClient obsClient = null;
        try {
            obsClient = this.buildObsClient();
            TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.PUT, expireSeconds);
            request.setBucketName(huaweiOBSConfig.getBucketName());
            request.setObjectKey(huaweiOBSConfig.getBasePath() + "/" + path + "/" + fileName);
            TemporarySignatureResponse response = obsClient.createTemporarySignature(request);

            log.info("Creating bucket using temporary signature url:");
            log.info("\t" + response.getSignedUrl());
            return HuaweiOBSTemporarySignature.builder()
                    .signedUrl(response.getSignedUrl())
                    .actualSignedRequestHeaders(response.getActualSignedRequestHeaders())
                    .build();
        } catch (ObsException e) {
            log.error("CreateBucket failed");
            // 请求失败,打印http状态码
            log.error("HTTP Code:" + e.getResponseCode());
            // 请求失败,打印服务端错误码
            log.error("Error Code:" + e.getErrorCode());
            // 请求失败,打印详细错误信息
            log.error("Error Message:" + e.getErrorMessage());
            // 请求失败,打印请求id
            log.error("Request ID:" + e.getErrorRequestId());
            log.error("Host ID:" + e.getErrorHostId());
            e.printStackTrace();
            throw new BusinessException(ResCode.HUAWEI_OBS_CREATE_TEMPORARY_SIGNATURE_FAIL);
        } catch (Exception e) {
            System.out.println("CreateBucket failed");
            // 其他异常信息打印
            e.printStackTrace();
            throw new BusinessException(ResCode.HUAWEI_OBS_CREATE_TEMPORARY_SIGNATURE_FAIL);
        } finally {
            if (StringUtils.isNotNull(obsClient)) {
                try {
                    obsClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("创建临时链接失败");
                }
            }
        }
    }

    private ObsClient buildObsClient() {
        return new ObsClient(huaweiOBSConfig.getAccessKey(),
                huaweiOBSConfig.getAccessSecret(), huaweiOBSConfig.getEndPoint());
    }

}
