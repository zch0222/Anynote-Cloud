package com.anynote.auth.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 重置密码传输类
 * @author 称霸幼儿园
 */
@Data
public class ResetPasswordDTO {

    @NotNull(message = "旧密码不能为空")
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,15}$",
            message = "密码必须是8到15位，且包含大写字母、小写字母和数字")
    private String newPassword;
}
