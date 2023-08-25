package com.anynote.core.web.enums;

/**
 * http状态码枚举类
 * @author 称霸幼儿园
 */
public enum HttpStatusEnum {
    /**
     * 操作成功
     */
    SUCCESS(200),

    /**
     * 未授权
     */
    UNAUTHORIZED(401),

    /**
     * 未找到
     */
    NOT_FOUND(404),

    /**
     * 系统内部错误
     */
    ERROR(500)
    ;

    private final int value;

    HttpStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
