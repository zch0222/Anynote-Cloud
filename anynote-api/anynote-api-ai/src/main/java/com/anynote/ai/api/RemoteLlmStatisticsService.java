package com.anynote.ai.api;

import com.anynote.ai.api.factory.RemoteLlmStatisticsFallbackFactory;
import com.anynote.ai.api.factory.RemoteTranslateFallbackFactory;
import com.anynote.ai.api.model.dto.LlmStatisticsCreateDTO;
import com.anynote.core.constant.ServiceNameConstants;
import com.anynote.core.web.model.bo.ResData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteLlmStatisticsService",
        value = ServiceNameConstants.AI_NIO_SERVICE, fallbackFactory = RemoteLlmStatisticsFallbackFactory.class)
public interface RemoteLlmStatisticsService {

    @PostMapping("llmStatistics")
    public ResData<Long> createLlmStatistics(@RequestHeader("from-source") String fromSource,
                                             @RequestBody @Validated LlmStatisticsCreateDTO llmStatisticsCreateDTO);
}
