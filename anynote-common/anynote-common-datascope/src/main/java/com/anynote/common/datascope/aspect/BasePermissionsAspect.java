package com.anynote.common.datascope.aspect;

import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.BaseEntity;
import com.anynote.system.api.model.bo.LoginUser;

import java.util.HashMap;
import java.util.Map;

public class BasePermissionsAspect {

    /**
     * LoginUser存储
     */
    public final static String LOGIN_USER_PARAMS_MAP_KEY = "LOGIN_USER";



    protected void addLoginUser(BaseEntity queryParam, LoginUser loginUser) {
        if (StringUtils.isNull(queryParam.getParams())) {
            Map<String, Object> map = new HashMap<>();
            map.put(LOGIN_USER_PARAMS_MAP_KEY, loginUser);
            queryParam.setParams(map);
        }
        else {
            queryParam.getParams().put(LOGIN_USER_PARAMS_MAP_KEY, loginUser);
        }
    }

}
