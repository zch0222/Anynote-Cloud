package com.anynote.core.exception.auth;

import com.anynote.core.web.enums.ResCode;

/**
 * Token 验证出错
 * @author 称霸幼儿园
 */
public class TokenException extends AuthException {
    public TokenException() {
        super();
    }

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, ResCode resCode) {
        super(message, resCode);
    }

    public TokenException(ResCode resCode) {
        super(resCode);
    }
}
