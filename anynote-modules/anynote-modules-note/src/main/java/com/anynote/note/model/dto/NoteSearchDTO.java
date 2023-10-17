package com.anynote.note.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 称霸幼儿园
 */
@Data
public class NoteSearchDTO {

    @ApiModelProperty(value = "关键词")
    @NotBlank(message = "关键词不能为空")
    private String keyword;

    /**
     * 页码不能为空
     */
    @NotNull(message = "搜索页码不能为空")
    @Min(value = 1, message = "页码不能小于1")
    private Integer page;

    /**
     * 页面的容量不能为空
     */
    @NotNull(message = "搜索页面容量不能为空")
    @Min(value = 1, message = "页面容量小于1")
    private Integer pageSize;

}
