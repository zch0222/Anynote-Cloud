package com.anynote.jobhandler;

import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.utils.DateUtils;
import com.anynote.core.utils.RemoteResDataUtil;
import com.anynote.system.api.RemoteSysApiStatisticsService;
import com.anynote.core.constant.SysApiStatisticsInterval;
import com.anynote.core.constant.SysApiStatisticsType;
import com.anynote.system.api.model.dto.ApiStatisticsCreateDTO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class SysApiStatisticsHandler {


    @Resource
    private RemoteSysApiStatisticsService remoteSysApiStatisticsService;

    private Long createApiStatistics(ApiStatisticsCreateDTO apiStatisticsCreateDTO) {
        return RemoteResDataUtil.getResData(remoteSysApiStatisticsService.createApiStatistics(apiStatisticsCreateDTO,
                SecurityConstants.INNER));
    }

    @XxlJob("llmApiStatisticsHourCreate")
    public void llmApiStatisticsHourCreate() {
        log.info("新建下一小时LLM API统计");
        try {
            createApiStatistics(ApiStatisticsCreateDTO.builder()
                    .startTime(DateUtils.getStartOfNextHour())
                    .endTime(DateUtils.getEndOfNextHour())
                    .type(SysApiStatisticsType.LLM)
                    .statisticsInterval(SysApiStatisticsInterval.HOUR)
                    .build());
        } catch (Exception e) {
            log.error("新建下一小时LLM API统计失败", e);
        }
    }

    @XxlJob("llmApiStatisticsMinuteCreate")
    public void llmApiStatisticsMinuteCreate() {
        log.info("新建下一分钟LLM API统计");
        try {
            createApiStatistics(ApiStatisticsCreateDTO.builder()
                    .startTime(DateUtils.getStartOfNextMinute())
                    .endTime(DateUtils.getEndOfNextMinute())
                    .type(SysApiStatisticsType.LLM)
                    .statisticsInterval(SysApiStatisticsInterval.MINUTE)
                    .build());
        } catch (Exception e) {
            log.error("新建下一分钟LLM API统计失败", e);
        }
    }

    @XxlJob("llmApiStatisticsDayCreate")
    public void llmApiStatisticsDailyCreate() {
        log.info("新建明天LLM API统计");
        try {
            createApiStatistics(ApiStatisticsCreateDTO.builder()
                    .startTime(DateUtils.getStartOfNextDay())
                    .endTime(DateUtils.getEndOfNextDay())
                    .type(SysApiStatisticsType.LLM)
                    .statisticsInterval(SysApiStatisticsInterval.DAY)
                    .build());
        } catch (Exception e) {
            log.error("新建明天LLM API统计失败", e);
        }
    }




}
