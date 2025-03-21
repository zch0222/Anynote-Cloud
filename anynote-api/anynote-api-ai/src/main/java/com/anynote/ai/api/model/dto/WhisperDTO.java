package com.anynote.ai.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WhisperDTO {

    @NotEmpty(message = "对象名称不能为空")
    private String objectName;

    @NotEmpty(message = "语言不能为空")
    private String language;
}
