package com.anynote.auth.service.impl;

import com.anynote.auth.service.LoginService;
import com.anynote.core.exception.auth.LoginException;
import com.anynote.system.api.model.bo.LoginUser;
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
        if(StringUtils.isAnyBlank(username, password)) {
            throw new LoginException("用户名密码错误")
        }
        return null;
    }
}
