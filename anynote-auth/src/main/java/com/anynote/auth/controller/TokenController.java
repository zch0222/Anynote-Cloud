package com.anynote.auth.controller;

import com.anynote.core.web.model.bo.ResData;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证 Controller
 * @author 称霸幼儿园
 */
@Api("Token Controller")
@RestController
public class TokenController {

    @PostMapping("login")
    public ResData<String> login() {
        return ResData.success("hello");
    }
}
