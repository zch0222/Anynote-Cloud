package com.anynote.note.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

}
