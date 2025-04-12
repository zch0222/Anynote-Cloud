package com.anynote.system.api.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@TableName("sys_api_statistics")
@NoArgsConstructor
@AllArgsConstructor
public class SysApiStatisticsPO {
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
     * 统计间隔 0.分钟 1.小时 2.天 4.周 5.月
     */
    private Integer statisticsInterval;

    /**
     * 删除标志(0标识未删除 1表示删除)
     */
    @TableLogic
    @TableField("is_delete")
    private Integer deleted;

    private Date createTime;

    private Date updateTime;
}
