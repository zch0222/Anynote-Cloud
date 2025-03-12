package com.anynote.file.api.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OssSliceUploadTaskCreatePublicDTO {

    /**
     * 文件名
     */
    @NotNull(message = "文件名称不能为空")
    private String fileName;

    /**
     * 文件哈希
     */
    @NotNull(message = "文件哈希不能为空")
    private String hash;

    /**
     * 文件大小
     */
    @NotNull(message = "文件大小不能为空")
    private Double fileSize;

    /**
     * contentType
     */
    @NotNull(message = "contentType不能为空")
    private String contentType;
}
