package com.anynote.common.security.token;

import com.anynote.common.security.properties.JWTTokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Token 工具
 * @author 称霸幼儿园
 */
@Component
public class TokenUtil {

    @Autowired
    private JWTTokenProperties jwtTokenProperties;



}
