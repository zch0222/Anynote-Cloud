package com.anynote.jobhandler;

import com.anynote.ai.api.RemoteLlmStatisticsService;
import com.anynote.ai.api.model.dto.LlmStatisticsCreateDTO;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.DateUtils;
import com.anynote.core.utils.RemoteResDataUtil;
import com.google.gson.Gson;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class LlmStatisticsHandler {

    @Resource
    private Gson gson;

    @Resource
    private RemoteLlmStatisticsService remoteLlmStatisticsService;

    @XxlJob("llmStatisticsDailyCreate")
    public void llmStatisticsDailyCreate() {
        log.info("新建明天大模型调用统计");
        try {
            RemoteResDataUtil.getResData(remoteLlmStatisticsService.createLlmStatistics(SecurityConstants.INNER,
                    LlmStatisticsCreateDTO.builder()
                            .startTime(DateUtils.getStartOfNextDay())
                            .endTime(DateUtils.getEndOfNextDay())
                            .type(0)
                            .build()));
        } catch (BusinessException e) {
            log.error("新建明天大模型调用统计失败", e);
        }

    }


}
