package com.anynote.ai.nio.service;

import com.anynote.ai.api.model.po.LlmStatisticsPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
public interface LlmStatisticsService extends IService<LlmStatisticsPO> {

    /**
     * 增加调用次数
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public void increaseUsageCount(Date startTime, Date endTime);
}
