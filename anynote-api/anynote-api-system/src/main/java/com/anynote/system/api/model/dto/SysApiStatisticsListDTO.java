package com.anynote.system.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysApiStatisticsListDTO {

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endTime;

    /**
     * 记录类型 0.LLM大语言模型
     */
    @NotNull(message = "记录类型不能为空")
    private Integer type;

    /**
     * 统计间隔  0.分钟 1.小时 2.天 3.周 4.月 5.年
     */
    @NotNull(message = "统计间隔不能为空")
    private Integer statisticsInterval;
}
