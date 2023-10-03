package com.anynote.file.api.factory;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.enums.ResCode;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.RemoteFileService;
import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.api.model.bo.FileUploadParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
            public ResData<FileDTO> uploadFile(@RequestParam("file") @NotNull(message = "文件不能为空") MultipartFile file,
                                               @RequestParam("path") @NotBlank(message = "文件路径不能为空") String path) {
                return ResUtil.error(ResCode.INNER_FILE_SERVICE_ERROR);
            }
        };
    }
}
