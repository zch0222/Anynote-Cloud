package com.anynote.core.web.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PageDTO {
    @Min(value = 1, message = "页码错误")
    private Integer page;

    @Max(value = 50, message = "页面容量错误")
    @Min(value = 1, message = "页面容量错误")
    private Integer pageSize;
}
