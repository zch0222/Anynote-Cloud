package com.anynote.note.datascope.annotation;

import com.anynote.common.datascope.enums.Logical;
import com.anynote.note.enums.NotePermissions;
import kotlin.annotation.AnnotationRetention;

import java.lang.annotation.*;

/**
 * 需要的笔记权限
 * @author 称霸幼儿园
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresNotePermissions {
    /**
     * 需要校验的权限码
     */
    NotePermissions value();

    /**
     * 验证模式：AND | OR，默认AND
     */
    Logical logical() default Logical.AND;
}
