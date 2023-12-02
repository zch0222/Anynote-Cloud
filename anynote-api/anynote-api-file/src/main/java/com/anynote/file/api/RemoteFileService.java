package com.anynote.file.api;

import com.anynote.core.constant.ServiceNameConstants;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.config.MultipartSupportConfig;
import com.anynote.file.api.factory.RemoteFileFallbackFactory;
import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.api.model.bo.FileUploadParam;
import com.anynote.file.api.model.bo.UploadProgress;
import com.anynote.file.api.model.po.FilePO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResData<FilePO> uploadFile(@RequestPart("file") @NotNull(message = "文件不能为空") MultipartFile file,
                                      @RequestPart("path") @NotNull(message = "文件路径不能为空") String path,
                                      @RequestPart("userId") @NotNull(message = "用户id不能为空") Long userId,
                                      @RequestPart("uploadId") @NotNull(message = "上传ID不能为空") String uploadId,
                                      @RequestParam("source") @NotNull(message = "文件来源不能为空") Integer source);

    @GetMapping("files/progress/{uploadId}")
    public ResData<UploadProgress> getFileUploadProgress(@PathVariable("uploadId") String uploadId);


}
