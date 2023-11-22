package com.anynote.core.validation.aspect;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.utils.file.FileTypeUtils;
import com.anynote.core.validation.annotation.Upload;
import com.anynote.core.validation.enums.FileType;
import com.anynote.core.web.enums.ResCode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件上传校验切片
 * @author 称霸幼儿园
 */
@Component
@Aspect
@Order(1)
public class UploadAspect {


    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;


    @Before("@annotation(upload)")
    public void doBefore(JoinPoint joinPoint, Upload upload) {
        Object[] args = joinPoint.getArgs();
        List<String> allowExtensions = new ArrayList<>();
        for (FileType fileType : upload.value()) {
            allowExtensions.addAll(FileTypeUtils.getAllowExtensions(fileType));
        }
        for (Object arg : args) {
            if (arg instanceof MultipartFile) {
                validate((MultipartFile) arg, upload, allowExtensions);
            }
        }
    }

    private void validate(MultipartFile file, Upload upload, List<String> allowExtensions) {
        int filenameLength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (filenameLength > UploadAspect.DEFAULT_FILE_NAME_LENGTH) {
            throw new BusinessException(StringUtils.format("文件名称长度不能超过%d个字符", UploadAspect.DEFAULT_FILE_NAME_LENGTH), ResCode.USER_UPLOAD_FILE_NAME_LENGTH_LIMIT_EXCEEDED);
        }
        long size = file.getSize();
        if (size > upload.max() * 1024 * 1024) {
            throw new BusinessException(StringUtils.format("文件大小不能超过%dMB", upload.max()), ResCode.USER_UPLOAD_SIZE_LIMIT_EXCEEDED);
        }
        String extension = FileTypeUtils.getExtension(file);
        if (!allowExtensions.contains(extension)) {
            throw new BusinessException(StringUtils.format("文件类型不支持，支持的文件类型为: %s", String.join(",", allowExtensions)),
                    ResCode.USER_UPLOAD_TYPE_NOT_MATCH);
        }
    }

}
