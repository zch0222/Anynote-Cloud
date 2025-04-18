package com.anynote.ai.api;

import com.anynote.ai.api.factory.RemoteLlmStatisticsFallbackFactory;
import com.anynote.ai.api.model.dto.LlmStatisticsCreateDTO;
import com.anynote.ai.api.model.dto.LlmStatisticsQueryDTO;
import com.anynote.ai.api.model.vo.StatisticsVO;
import com.anynote.core.constant.ServiceNameConstants;
import com.anynote.core.web.model.bo.ResData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(contextId = "remoteLlmStatisticsService",
        value = ServiceNameConstants.AI_NIO_SERVICE, fallbackFactory = RemoteLlmStatisticsFallbackFactory.class)
public interface RemoteLlmStatisticsService {

    @PostMapping("llmStatistics")
    public ResData<Long> createLlmStatistics(@RequestHeader("from-source") String fromSource,
                                             @RequestBody @Validated LlmStatisticsCreateDTO llmStatisticsCreateDTO);

    @GetMapping("llmStatistics/list")
    public ResData<List<StatisticsVO>> getLlmStatistics(@RequestHeader("from-source") String fromSource,
                                                        @SpringQueryMap LlmStatisticsQueryDTO llmStatisticsQueryDTO);
}
