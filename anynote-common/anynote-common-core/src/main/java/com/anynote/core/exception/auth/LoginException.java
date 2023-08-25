package com.anynote.core.exception.auth;

import com.anynote.core.web.enums.ResCode;

/**
 * 登录鉴权异常
 * @author 称霸幼儿园
 */
public class LoginException extends AuthException {

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, ResCode resCode) {
        super(message, resCode);
    }

    public LoginException(ResCode resCode) {
        super(resCode);
    }
}
