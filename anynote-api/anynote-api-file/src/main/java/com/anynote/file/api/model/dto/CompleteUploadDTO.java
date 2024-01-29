package com.anynote.file.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 完成华为OBS文件上传
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteUploadDTO {

    /**
     * 上传ID
     */
    @NotBlank(message = "上传ID不能为空")
    private String uploadId;


    /**
     * 文件哈希
     */
    @NotBlank(message = "文件哈希不能为空")
    private String hash;

}
