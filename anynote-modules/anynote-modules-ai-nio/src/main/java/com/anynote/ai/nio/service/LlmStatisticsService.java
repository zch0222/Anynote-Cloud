package com.anynote.ai.nio.service;

import com.anynote.ai.api.model.dto.LlmStatisticsQueryDTO;
import com.anynote.ai.api.model.po.LlmStatisticsPO;
import com.anynote.ai.api.model.vo.StatisticsVO;
import com.baomidou.mybatisplus.extension.service.IService;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

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

    public Mono<List<StatisticsVO>> getLlmStatistics(LlmStatisticsQueryDTO llmStatisticsQueryDTO);
}
