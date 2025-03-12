package com.anynote.file.api.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OssSliceUploadComposeDTO {

    @NotNull(message = "uploadId不能为空")
    private String uploadId;
}
