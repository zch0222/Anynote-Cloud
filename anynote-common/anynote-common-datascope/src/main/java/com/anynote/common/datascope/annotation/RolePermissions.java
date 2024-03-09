package com.anynote.common.datascope.annotation;

import com.anynote.core.enums.Role;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RolePermissions {

    Role[] value() default {};
}
