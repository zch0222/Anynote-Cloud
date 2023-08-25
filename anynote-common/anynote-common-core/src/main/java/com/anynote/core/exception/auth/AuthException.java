package com.anynote.core.exception.auth;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.web.enums.ResCode;

/**
 * @author 称霸幼儿园
 */
public class AuthException extends BusinessException {
    public AuthException() {
        super(ResCode.AUTH_ERROR);
    }

    public AuthException(String message) {
        super(message ,ResCode.AUTH_ERROR);
    }

    public AuthException(String message, ResCode resCode) {
        super(message, resCode);
    }

    public AuthException(ResCode resCode) {
        super(resCode);
    }


}
