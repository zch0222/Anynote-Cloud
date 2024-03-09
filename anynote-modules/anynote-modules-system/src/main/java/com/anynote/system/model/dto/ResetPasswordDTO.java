package com.anynote.system.model.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ResetPasswordDTO {

    @NotNull(message = "用户ID不能为空")
    private Long userId;


    @NotBlank(message = "密码不能为空")
    @Size(max = 15, min = 6, message = "密码长度必须为6～15位")
    private String newPassword;
}
