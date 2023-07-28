package com.anynote.system.api.model;

import lombok.Data;

/**
 * Token 实体类
 *
 * @author 称霸幼儿园
 */
@Data
public class Token {
    /**
     * 访问Token
     */
    private String accessToken;

    /**
     * 刷新Token
     */
    private String refreshToken;

    /**
     * accessToken过期时间
     */
    private Long accessTokenExpirationTime;
}
