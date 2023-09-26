package com.anynote.auth.model.dto;

import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.bo.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录返回传输类
 *
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    private String username;

    /**
     * 昵称
     */
    private String nickname;

    private String role;

    /**
     * 头像地址
     */
    private String avatar;

    private Token token;

    public LoginDTO(LoginUser loginUser) {
        this.username = loginUser.getUsername();
        this.nickname = loginUser.getSysUser().getNickname();
        this.role = loginUser.getRole();
        this.avatar = loginUser.getSysUser().getAvatar();
        this.token = loginUser.getToken();
    }

}
