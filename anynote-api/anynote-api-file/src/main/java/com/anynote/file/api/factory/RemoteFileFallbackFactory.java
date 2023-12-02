package com.anynote.file.api.factory;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.enums.ResCode;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.RemoteFileService;
import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.api.model.bo.FileUploadParam;
import com.anynote.file.api.model.bo.UploadProgress;
import com.anynote.file.api.model.po.FilePO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文件服务降级
 * @author 称霸幼儿园
 */
@Slf4j
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService> {


    @Override
    public RemoteFileService create(Throwable cause) {
        log.error("文件服务调用失败: {}", cause.getMessage());
        return new RemoteFileService() {

            @Override
            public ResData<FilePO> uploadFile(MultipartFile file, String path, Long userId, String uploadId, Integer source) {
                return ResUtil.error(ResCode.INNER_FILE_SERVICE_ERROR);
            }

            @Override
            public ResData<UploadProgress> getFileUploadProgress(String uploadId) {
                return ResUtil.error(ResCode.INNER_FILE_SERVICE_ERROR);
            }
        };
    }
}
