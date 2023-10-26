package com.anynote.note.datascope.annotation;

import com.anynote.note.enums.NoteTaskPermissions;

import java.lang.annotation.*;

/**
 * @author 称霸幼儿园
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresNoteTaskPermissions {

    /**
     * 需要的权限
     */
    NoteTaskPermissions value();

    String message() default "权限不足";

}
