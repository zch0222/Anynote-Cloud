package com.anynote.ai.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
public class GetMoocVideoSummarizesByMoocIdDTO {

    @NotNull(message = "慕课id不能为空")
    private Long moocId;

    @NotNull(message = "慕课Item Id不能为空")
    private Long moocItemId;
}
