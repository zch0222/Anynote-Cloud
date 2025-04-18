package com.anynote.system.api.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UnBanUserDTO {

    @NotNull(message = "用户id不能为空")
    private Long userId;
}
