package com.anynote.system.api.model.bo;

import lombok.Data;

/**
 * @author 称霸幼儿园
 */
@Data
public class SysUserUpdateParam extends SysUserQueryParam {

    /**
     * 密码
     */
    private String password;
}
