package com.anynote.common.security.annotation;

import java.lang.annotation.*;

/**
 * 内部认证注解
 *
 * @author 称霸幼儿园
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerAuth {

    /**
     * 是否校验用户信息
     * @return
     */
    boolean isUser() default false;
}
