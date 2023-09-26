package com.anynote.auth.service.impl;

import com.anynote.auth.service.LoginService;
import com.anynote.auth.service.PasswordService;
import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.constant.UserConstants;
import com.anynote.core.enums.UserStatus;
import com.anynote.core.exception.auth.LoginException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.system.api.RemoteUserService;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.bo.Token;
import com.anynote.system.api.model.po.SysUser;
import org.apache.catalina.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录服务
 * @author 称霸幼儿园
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private TokenUtil tokenUtil;

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

        ResData<LoginUser> userResult = remoteUserService.getUserInfo(username);

        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            throw new LoginException(ResCode.USER_NOT_FOUND);
        }

        if (!ResData.SUCCESS.equals(userResult.getCode())) {
            throw new LoginException(userResult.getMsg());
        }

        LoginUser loginUser = userResult.getData();
        SysUser sysUser = loginUser.getSysUser();

        if (UserStatus.DELETED.getCode() == sysUser.getStatus()) {
            throw new LoginException(ResCode.USER_DELETED);
        }

        if (UserStatus.DISABLE.getCode() == sysUser.getStatus()) {
            throw new LoginException(ResCode.USER_DISABLED);
        }

        passwordService.validate(sysUser, password);

        Token token = tokenUtil.createToken(loginUser);

        return loginUser;
    }
}
