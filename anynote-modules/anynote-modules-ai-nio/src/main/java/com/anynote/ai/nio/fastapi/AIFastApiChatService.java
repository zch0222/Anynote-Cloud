package com.anynote.ai.nio.fastapi;

import com.anynote.ai.nio.fastapi.dto.FastApiChatCompletionsDTO;
import com.anynote.ai.nio.fastapi.vo.FastApiChatCompletionsVO;
import com.anynote.ai.nio.service.LlmStatisticsService;
import com.anynote.common.redis.service.ConfigService;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.constant.SysApiStatisticsType;
import com.anynote.core.utils.DateUtils;
import com.anynote.core.utils.RemoteResDataUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.system.api.RemoteSysApiStatisticsService;
import com.anynote.system.api.model.dto.IncreaseApiUsageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private RemoteSysApiStatisticsService remoteSysApiStatisticsService;

    private void increaseLlmUsageCount() {
//        try {
//            ioExecutor.execute(() -> {
//                llmStatisticsService.increaseUsageCount(DateUtils.getStartOfDay(), DateUtils.getEndOfDay());
//            });
//        } catch (Exception e) {
//            log.error("记录LLM调用次数失败", e);
//        }
        try {
            Date now = new Date();
            ioExecutor.execute(() -> {
                RemoteResDataUtil.getResData(remoteSysApiStatisticsService.increaseUsage(IncreaseApiUsageDTO.builder()
                        .time(now)
                        .type(SysApiStatisticsType.LLM)
                        .build(), SecurityConstants.INNER));
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
