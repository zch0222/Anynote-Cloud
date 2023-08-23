package com.anynote.common.security.enums;

/**
 * 安全相关常量
 * @author 称霸幼儿园
 */
public enum SecurityEnum {

    ACCESS_TOKEN_HEADER("accessToken"),
    USER_CONTEXT("userContext"),
    JWT_SECRET("secret"),
    UUID("uuid");

    private final String value;

    SecurityEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
