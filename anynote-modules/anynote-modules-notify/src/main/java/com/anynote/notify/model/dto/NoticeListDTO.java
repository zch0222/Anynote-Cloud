package com.anynote.notify.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 通知列表DTO
 */
@Data
public class NoticeListDTO {

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码错误")
    private Integer page;

    /**
     * 页面大小
     */
    @NotNull(message = "页大小不能为空")
    @Min(value = 1, message = "页大小错误")
    private Integer pageSize;
}
