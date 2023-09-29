package com.anynote.core.exception.user;

import com.anynote.core.web.enums.ResCode;

/**
 * 用户请求参数错误
 * @author 称霸幼儿园
 */
public class UserParamException extends UserException{
    public UserParamException() {
        super();
    }

    public UserParamException(String message) {
        super(message);
    }

    public UserParamException(String message, ResCode resCode) {
        super(message, resCode);
    }

    public UserParamException(ResCode resCode) {
        super(resCode);
    }

}
