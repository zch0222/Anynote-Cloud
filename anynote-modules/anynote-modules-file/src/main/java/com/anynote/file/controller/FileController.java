package com.anynote.file.controller;

import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.api.model.bo.FileUploadParam;
import com.anynote.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("files")
@Validated
public class FileController {

    @Autowired
    private FileService fileService;

    @InnerAuth
    @PostMapping
    public ResData<FileDTO> uploadFile(@RequestPart("file") @NotNull(message = "文件不能为空") MultipartFile file,
                                       @RequestPart("path") @NotBlank(message = "文件路径不能为空") String path) {
        return ResData.success(fileService.upload(file, path));
    }
}
