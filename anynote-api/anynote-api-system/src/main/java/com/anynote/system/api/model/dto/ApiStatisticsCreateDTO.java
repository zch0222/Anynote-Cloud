package com.anynote.system.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiStatisticsCreateDTO {

    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    @NotNull(message = "API类型不能为空")
    private Integer type;

    @NotNull(message = "间隔不能为空")
    private Integer statisticsInterval;
}
