package com.anynote.common.datascope.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤 Mapper 拦截注解
 * @author 称霸幼儿园
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScopeInterceptor {

}
