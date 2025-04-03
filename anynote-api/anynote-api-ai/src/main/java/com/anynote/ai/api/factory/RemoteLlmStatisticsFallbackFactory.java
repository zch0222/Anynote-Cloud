package com.anynote.ai.api.factory;

import com.anynote.ai.api.RemoteLlmStatisticsService;
import com.anynote.ai.api.model.dto.LlmStatisticsCreateDTO;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.ResData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteLlmStatisticsFallbackFactory implements FallbackFactory<RemoteLlmStatisticsService> {

    @Override
    public RemoteLlmStatisticsService create(Throwable cause) {
        return new RemoteLlmStatisticsService() {
            @Override
            public ResData<Long> createLlmStatistics(String fromSource, LlmStatisticsCreateDTO llmStatisticsCreateDTO) {
                throw new BusinessException("调用/aiNio/llmStatistics失败");
            }
        };
    }
}
