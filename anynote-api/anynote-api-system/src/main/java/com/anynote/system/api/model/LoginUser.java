package com.anynote.system.api.model;

import com.anynote.system.api.demain.SysUser;
import lombok.Data;
import java.util.Set;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author 称霸幼儿园
 */
@Data
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户 Token
     */
    private Token token;

    /**
     * 用户id
     */
    private Long user_id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 用户信息
     */
    private SysUser sysUser;

}
