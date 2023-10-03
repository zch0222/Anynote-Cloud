package com.anynote.file.api;

import com.anynote.core.constant.ServiceNameConstants;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.config.MultipartSupportConfig;
import com.anynote.file.api.factory.RemoteFileFallbackFactory;
import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.api.model.bo.FileUploadParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.awt.*;

/**
 * @author 称霸幼儿园
 */
@FeignClient(contextId = "remoteFileService",
        value = ServiceNameConstants.FILE_SERVICE,
        fallbackFactory = RemoteFileFallbackFactory.class,
        configuration = MultipartSupportConfig.class)
public interface RemoteFileService {

    @PostMapping(value = "files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResData<FileDTO> uploadFile(@RequestPart("file") @NotNull(message = "文件不能为空") MultipartFile file,
                                       @RequestPart("path") @NotBlank(message = "文件路径不能为空") String path);
}
