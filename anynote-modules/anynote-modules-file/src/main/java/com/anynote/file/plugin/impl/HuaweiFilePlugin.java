package com.anynote.file.plugin.impl;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.file.enums.OssTypeEnum;
import com.anynote.file.model.bo.HuaweiOBSConfig;
import com.anynote.file.plugin.FilePlugin;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectRequest;
import com.obs.services.model.PutObjectResult;
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

    private ObsClient buildObsClient() {
        return new ObsClient(huaweiOBSConfig.getAccessKey(),
                huaweiOBSConfig.getAccessSecret(), huaweiOBSConfig.getEndPoint());
    }

}
