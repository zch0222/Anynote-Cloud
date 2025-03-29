package com.anynote.ai.nio.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhisperTaskStatusVOV1 {

    private Long taskId;

    private Integer whisperTaskStatus;
}
