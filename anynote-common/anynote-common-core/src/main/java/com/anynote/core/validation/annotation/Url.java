package com.anynote.core.validation.annotation;

import java.lang.annotation.*;

/**
 *
 * @author 称霸幼儿园
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Url {

    /**
     * 需要校验的字段名称
     */
    String value();

    /**
     * 参数名称
     */
    String param();
}
