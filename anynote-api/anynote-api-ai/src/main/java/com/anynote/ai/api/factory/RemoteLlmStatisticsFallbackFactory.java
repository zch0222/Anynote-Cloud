package com.anynote.ai.api.factory;

import com.anynote.ai.api.RemoteLlmStatisticsService;
import com.anynote.ai.api.model.dto.LlmStatisticsCreateDTO;
import com.anynote.ai.api.model.dto.LlmStatisticsQueryDTO;
import com.anynote.ai.api.model.vo.StatisticsVO;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.web.model.bo.ResData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteLlmStatisticsFallbackFactory implements FallbackFactory<RemoteLlmStatisticsService> {

    @Override
    public RemoteLlmStatisticsService create(Throwable cause) {
        return new RemoteLlmStatisticsService() {
            @Override
            public ResData<Long> createLlmStatistics(String fromSource, LlmStatisticsCreateDTO llmStatisticsCreateDTO) {
                throw new BusinessException("调用/aiNio/llmStatistics失败 GET");
            }

            @Override
            public ResData<List<StatisticsVO>> getLlmStatistics(String fromSource, LlmStatisticsQueryDTO llmStatisticsQueryDTO) {
                throw new BusinessException("调用/aiNio/llmStatistics失败 POST");
            }
        };
    }
}
