package com.anynote.core.exception.auth;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.web.enums.ResCode;

/**
 * 内部调用错误类
 * @author 称霸幼儿园
 */
public class InnerAuthException extends AuthException {

    public InnerAuthException() {
        super(ResCode.UNAUTHORIZED_ERROR);
    }

    public InnerAuthException(String message) {
        super(message, ResCode.UNAUTHORIZED_ERROR);
    }

}
