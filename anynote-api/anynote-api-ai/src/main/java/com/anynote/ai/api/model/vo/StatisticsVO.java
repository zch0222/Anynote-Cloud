package com.anynote.ai.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 大语言日志统计
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsVO {

    /**
     * 日志id
     */
    private Long id;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 调用统计
     */
    private Integer usageCount;

    /**
     * 记录类型 0.天
     */
    private Integer type;

}
