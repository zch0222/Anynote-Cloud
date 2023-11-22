package com.anynote.core.validation.annotation;

import com.anynote.core.validation.enums.FileType;

import java.lang.annotation.*;

/**
 *
 * @author 称霸幼儿园
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Upload {

    /**
     * 允许的文件类型
     * @return
     */
    FileType[] value();

    /**
     * 错误提示
     * @return
     */
    String message() default "文件上传失败";

    int max() default 50;
}
