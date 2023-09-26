package com.anynote.auth.model.dto;

import lombok.Data;

/**
 * 登录请求参数
 *
 * @author 称霸幼儿园
 */
@Data
public class LoginRequestDTO {

    private String username;

    private String password;
}
