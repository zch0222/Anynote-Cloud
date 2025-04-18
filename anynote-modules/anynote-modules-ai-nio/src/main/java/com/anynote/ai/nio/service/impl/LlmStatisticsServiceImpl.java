package com.anynote.ai.nio.service.impl;

import com.anynote.ai.api.model.dto.LlmStatisticsQueryDTO;
import com.anynote.ai.api.model.po.LlmStatisticsPO;
import com.anynote.ai.api.model.vo.StatisticsVO;
import com.anynote.ai.nio.mapper.LlmStatisticsMapper;
import com.anynote.ai.nio.service.LlmStatisticsService;
import com.anynote.core.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LlmStatisticsServiceImpl extends ServiceImpl<LlmStatisticsMapper, LlmStatisticsPO>
        implements LlmStatisticsService {

    @Override
    public void increaseUsageCount(Date startTime, Date endTime) {
        baseMapper.increaseUsageCount(startTime, endTime);
    }

    @Override
    public Mono<List<StatisticsVO>> getLlmStatistics(LlmStatisticsQueryDTO llmStatisticsQueryDTO) {
        return Mono.fromCallable(() -> {
            return this.baseMapper.selectList(new LambdaQueryWrapper<LlmStatisticsPO>()
                    .ge(StringUtils.isNotNull(llmStatisticsQueryDTO.getStartTime()),
                            LlmStatisticsPO::getStartTime, llmStatisticsQueryDTO.getStartTime())
                    .le(StringUtils.isNotNull(llmStatisticsQueryDTO.getEndTime()),
                            LlmStatisticsPO::getEndTime, llmStatisticsQueryDTO.getEndTime())
                    .eq(LlmStatisticsPO::getType, llmStatisticsQueryDTO.getType()))
                    .stream()
                    .map(llmStatisticsPO -> StatisticsVO.builder()
                            .id(llmStatisticsPO.getId())
                            .startTime(llmStatisticsPO.getStartTime())
                            .endTime(llmStatisticsPO.getEndTime())
                            .type(llmStatisticsPO.getType())
                            .usageCount(llmStatisticsPO.getUsageCount())
                            .build())
                    .collect(Collectors.toList());
        }).subscribeOn(Schedulers.boundedElastic()).log();
    }
}
