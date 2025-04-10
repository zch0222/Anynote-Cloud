package com.anynote.ai.api.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class LlmStatisticsQueryDTO {

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 记录类型 0.天
     */
    @NotNull(message = "类型不能为空")
    private Integer type;
}
