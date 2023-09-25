package com.anynote.auth.service.impl;

import com.anynote.auth.service.LoginService;
import com.anynote.core.constant.UserConstants;
import com.anynote.core.exception.auth.LoginException;
import com.anynote.system.api.model.bo.LoginUser;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 登录服务
 * @author 称霸幼儿园
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public LoginUser login(String username, String password) {
        // 用户名或密码为空
        if(StringUtils.isAnyBlank(username, password)) {
            // 日志记录（需要添加）
            throw new LoginException("用户名/密码必须填写");
        }
        // 密码不在指定范围内
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new LoginException("用户密码不在指定范围内");
        }

        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new LoginException("用户名不在指定范围内");
        }





        return null;
    }
}
