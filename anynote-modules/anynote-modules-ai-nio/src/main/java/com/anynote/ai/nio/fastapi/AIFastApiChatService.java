package com.anynote.ai.nio.fastapi;

import com.anynote.ai.nio.fastapi.dto.FastApiChatCompletionsDTO;
import com.anynote.ai.nio.fastapi.vo.FastApiChatCompletionsVO;
import com.anynote.ai.nio.service.LlmStatisticsService;
import com.anynote.common.redis.service.ConfigService;
import com.anynote.core.utils.DateUtils;
import com.anynote.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class AIFastApiChatService {

    @Autowired
    private WebClient.Builder webClientBuilder;

//    @Resource
//    private WebClient webClient;

//    @Resource
//    private AIFastApiProperties aiFastApiProperties;
    @Resource
    private ConfigService configService;

    @Resource
    private Executor ioExecutor;

    @Resource
    private LlmStatisticsService llmStatisticsService;

    private void increaseLlmUsageCount() {
        try {
            ioExecutor.execute(() -> {
                llmStatisticsService.increaseUsageCount(DateUtils.getStartOfDay(), DateUtils.getEndOfDay());
            });
        } catch (Exception e) {
            log.error("记录LLM调用次数失败", e);
        }
    }


    public Flux<FastApiChatCompletionsVO> chatCompletions(FastApiChatCompletionsDTO chatCompletionsDTO) {
        increaseLlmUsageCount();
        return webClientBuilder.build().post()
                .uri(StringUtils.format("{}/v1/chat/completions", configService.getAIServerAddress()))
                .header(HttpHeaders.AUTHORIZATION, configService.getAIServerAPIKey())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(chatCompletionsDTO))
                .retrieve()
                .bodyToFlux(FastApiChatCompletionsVO.class);
    }

}
