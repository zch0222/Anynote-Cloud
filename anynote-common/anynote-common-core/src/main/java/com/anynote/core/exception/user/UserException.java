package com.anynote.core.exception.user;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.web.enums.ResCode;

/**
 *
 * @author 称霸幼儿园
 */
public class UserException extends BusinessException {

    public UserException() {
        super(ResCode.USER_ERROR);
    }

    public UserException(String message) {
        super(message, ResCode.USER_ERROR);
    }

    public UserException(String message, ResCode resCode) {
        super(message, resCode);
    }

    public UserException(ResCode resCode) {
        super(resCode);
    }
}
