package com.anynote.file.service;

import com.anynote.file.api.model.bo.FileDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 * @author 称霸幼儿园
 */
public interface FileService {


    public FileDTO upload(MultipartFile file, String path);

}
