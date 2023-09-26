package com.anynote.core.web.enums;

/**
 * AJAX返回code枚举类
 * @author 称霸幼儿园
 */
public enum ResCode {

    SUCCESS("00000", "成功", "操作成功"),
    USER_ERROR("A0001", "用户端错误", "操作错误"),
    USER_REGISTER_ERROR("A0100", "用户注册错误", "注册失败"),
    USER_NOT_FOUND("A0201", "用户账户不存在", "用户账户不存在"),
    USER_DISABLED("A0202", "账户被禁用", "对不起，账户被禁用"),
    USER_DELETED("A0203", "账户被删除", "对不起，账户被删除"),
    USER_PASSWORD_TRY_ERROR("A0211", "用户输入密码错误次数超限", "用户输入密码错误次数超限"),
    USER_IDENTITY_VERIFICATION_FAILED("A0220", "用户身份校验失败", "用户身份校验失败"),


    AUTH_ERROR("A0300", "访问权限异常", "访问权限异常"),
    UNAUTHORIZED_ERROR("A0301", "访问未授权", "访问未授权"),
    TOKEN_EXPIRED("A0311", "授权已过期", "授权已过期"),
    ACCESS_TOKEN_NOT_FOUND("A0350", "未携带AccessToken", "访问未授权 "),

    BUSINESS_ERROR("B0001", "系统执行出错 ", "系统执行出错"),
    INNER_SERVICE_ERROR("B0400", "系统内部调用失败", "系统内部调用失败"),
    INNER_SYSTEM_SERVICE_ERROR("B0401", "系统模块内部调用失败", "系统模块内部调用失败")
    ;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误码描述
     */
    private final String description;

    /**
     * 返回消息
     */
    private final String msg;

    ResCode(String code, String description, String msg) {
        this.code = code;
        this.description = description;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getMsg() {
        return this.msg;
    }
}
