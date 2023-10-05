package com.anynote.core.utils;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

public class MultipartFileUtil {
    public static MultipartFile toMultipartFile(byte[] bytes, String fileName) {
        DiskFileItem fileItem = createFileItem(bytes, fileName);
        return new CommonsMultipartFile(fileItem);
    }

    private static DiskFileItem createFileItem(byte[] bytes, String fileName) {
        DiskFileItem fileItem = new DiskFileItem(fileName, "multipart/form-data", true,
                fileName, bytes.length, null);
        try {
            IOUtils.copy(new ByteArrayInputStream(bytes), fileItem.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return fileItem;
    }
}
