package com.anynote.common.datascope.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 *
 * @author 称霸幼儿园
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 用户表的别名
     */
    public String userAlias() default "sys_user";

    /**
     * 权限字符
     * @return
     */
    public String permission() default "";

    /**
     * 组织表的别名
     */
    public String organizationAlias() default "sys_organization";
}
