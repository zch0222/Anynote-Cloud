package com.anynote.system.api.model.bo;

import com.anynote.system.api.model.po.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户 Token
     */
    private Token token;

    /**
     * 用户id
     */
    private Long userId;

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
    private String role;

    /**
     * 用户信息
     */
    private SysUser sysUser;

    /**
     * 长期有效
     */
    private boolean longTerm;

    public LoginUser(SysUser sysUser, Set<String> permissions,
                     String role, boolean longTerm) {
        this.sysUser = sysUser;
        this.permissions = permissions;
        this.role = role;
        this.longTerm = longTerm;

        this.userId = sysUser.getId();
        this.username = sysUser.getUsername();
        this.loginTime = System.currentTimeMillis();
    }

}
