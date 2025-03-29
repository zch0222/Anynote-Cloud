package com.anynote.ai.nio.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MoocVideoSummarizeDTO {

    @NotNull(message = "慕课id不能为空")
    private Long moocId;

    @NotNull(message = "慕课Item id不能为空")
    private Long moocItemId;

    @NotNull(message = "模型不能为空")
    private String model;
}
