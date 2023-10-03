package com.anynote.file.plugin;

import com.anynote.file.enums.OssTypeEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件插件
 * @author 称霸幼儿园
 */
public interface FilePlugin {

    public OssTypeEnum getPluginOssType();

    public String multipartFileUpload(MultipartFile file, String path, String fileName);
}
