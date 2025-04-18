package com.anynote.system.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysApiStatisticsVO {
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
     * 记录类型 0.LLM大语言模型
     */
    private Integer type;

    /**
     * 统计间隔 0.分钟 1.小时 2.天 3.周 4.月 5.年
     */
    private Integer statisticsInterval;

    private Date createTime;

    private Date updateTime;

}
