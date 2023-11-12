package com.anynote.system.api.model.bo;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author 称霸幼儿园
 */
@Data
public class SysUserUpdateParam extends SysUserQueryParam {

    /**
     * 密码
     */
    @Size(max = 15, min = 6, message = "密码长度必须为6-15位")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).*$",
//            message = "密码必须包含大写字母、小写字母和特殊字符")
    private String password;
}
