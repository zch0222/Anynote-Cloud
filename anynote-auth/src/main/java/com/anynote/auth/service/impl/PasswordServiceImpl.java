package com.anynote.auth.service.impl;

import com.anynote.auth.service.PasswordService;
import com.anynote.common.redis.service.RedisService;
import com.anynote.common.security.utils.SecurityUtils;
import com.anynote.core.constant.CacheConstants;
import com.anynote.core.constant.Constants;
import com.anynote.core.exception.auth.LoginException;
import com.anynote.core.web.enums.ResCode;
import com.anynote.system.api.model.po.SysUser;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 密码服务
 *
 * @author 称霸幼儿园
 */
@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private RedisService redisService;

    private String getCacheKey(String username) {
        return CacheConstants.PASSWORD_ERROR_COUNT_KEY + username;
    }

    @Override
    public void validate(SysUser sysUser, String rawPassword) {
        String username = sysUser.getUsername();

        Integer retryCount = redisService.getCacheObject(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= Constants.PASSWORD_MAX_RETRY_COUNT) {
            String errorMessage = String.format("密码错误%d次，账户锁定%d分钟", Constants.PASSWORD_MAX_RETRY_COUNT, Constants.PASSWORD_MAX_RETRY_COUNT);
            throw new LoginException(errorMessage, ResCode.USER_PASSWORD_TRY_ERROR);
        }

        if (!this.matches(sysUser, rawPassword)) {
            retryCount = retryCount + 1;
            redisService.setCacheObject(getCacheKey(sysUser.getUsername()), retryCount, Constants.PASSWORD_ERROR_LACK_TIME, TimeUnit.MINUTES);
            throw new LoginException(ResCode.USER_IDENTITY_VERIFICATION_FAILED);
        }

        if (redisService.hasKey(getCacheKey(sysUser.getUsername()))) {
            redisService.deleteObject(getCacheKey(sysUser.getUsername()));
        }

    }

    private boolean matches(SysUser sysUser, String rawPassword) {
        return SecurityUtils.matchesPassword(rawPassword, sysUser.getPassword());
    }
}
