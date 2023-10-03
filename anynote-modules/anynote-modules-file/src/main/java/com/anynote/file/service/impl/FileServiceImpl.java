package com.anynote.file.service.impl;

import com.anynote.file.api.model.bo.FileDTO;
import com.anynote.file.factory.FilePluginFactory;
import com.anynote.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 笔记图片文件服务
 * @author 称霸幼儿园
 */
@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private FilePluginFactory filePluginFactory;

//    @Override
//    public FileDTO upload(NoteImageUploadParam uploadParam) {
//        NoteImage noteImage = NoteImage.builder()
//                .originalFileName(uploadParam.getFile().getOriginalFilename())
//                .fileName(UUID.randomUUID().toString().replace("-", "") + "_" + uploadParam.getFile().getOriginalFilename())
//                .us
//                .build();
//        String url = filePluginFactory.filePlugin()
//                .multipartFileUpload(uploadParam.getFile(),
//                        StringUtils.format("note/{}", uploadParam.getNoteId()),
//                        noteImage.getFileName());
//
//        return null;
//    }


    @Override
    public FileDTO upload(MultipartFile file, String path) {
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + file.getOriginalFilename();
        String url = filePluginFactory.filePlugin()
                .multipartFileUpload(file, path, fileName);
        return FileDTO.builder()
                .originalFileName(file.getOriginalFilename())
                .fileName(fileName)
                .url(url)
                .build();
    }
}
