package com.anynote.note.validate.annotation;

import javax.validation.Valid;
import java.lang.annotation.*;

/**
 * 分页参数验证
 * @author 称霸幼儿园
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageValid {
}
