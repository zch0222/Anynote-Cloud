package com.anynote.file.api.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadParam {

    @NotNull(message = "文件不能为空")
    private MultipartFile file;

    @NotBlank(message = "文件地址不能为空")
    private String path;
}
