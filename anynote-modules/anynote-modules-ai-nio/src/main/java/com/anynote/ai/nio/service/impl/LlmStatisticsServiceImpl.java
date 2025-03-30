package com.anynote.ai.nio.service.impl;

import com.anynote.ai.api.model.po.LlmStatisticsPO;
import com.anynote.ai.nio.mapper.LlmStatisticsMapper;
import com.anynote.ai.nio.service.LlmStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LlmStatisticsServiceImpl extends ServiceImpl<LlmStatisticsMapper, LlmStatisticsPO>
        implements LlmStatisticsService {

    @Override
    public void increaseUsageCount(Date startTime, Date endTime) {
        baseMapper.increaseUsageCount(startTime, endTime);
    }
}
