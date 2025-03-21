package com.anynote.note.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MoocAsrDTO {

    /**
     * 慕课id
     */
    @NotNull(message = "慕课id不能为空")
    private Long moocId;

    /**
     * 慕课item id
     */
    @NotNull(message = "慕课item id不能为空")
    private Long moocItemId;

    /**
     * 语言
     */
    @NotNull(message = "语言不能为空")
    private String language;
}
