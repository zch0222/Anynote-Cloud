package com.anynote.core.utils;

import org.springframework.aop.framework.AopContext;

public class AopUtil {


    public static <T> T getCurrentProxy(Class<T> tClass) {
        return (T) AopContext.currentProxy();
    }
}
