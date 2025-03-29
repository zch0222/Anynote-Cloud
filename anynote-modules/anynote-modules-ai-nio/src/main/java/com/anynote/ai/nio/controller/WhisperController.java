package com.anynote.ai.nio.controller;

import com.anynote.ai.api.model.vo.WhisperTaskStatusVO;
import com.anynote.ai.nio.model.bo.WhisperTaskQueryParam;
import com.anynote.ai.api.model.dto.WhisperDTO;
import com.anynote.ai.api.model.vo.WhisperSubmitVO;
import com.anynote.ai.nio.model.vo.WhisperTaskStatusVOV1;
import com.anynote.ai.nio.service.WhisperService;
import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@RestController
@RequestMapping("whisper")
@Slf4j
public class WhisperController {

    @Resource
    private WhisperService whisperService;


    @PostMapping("")
    public Flux<ServerSentEvent<String>> whisper(@Validated @RequestBody WhisperDTO whisperDTO) {
        return whisperService.whisper(whisperDTO);
    }

    @InnerAuth
    @PostMapping("submit")
    public Mono<ResData<WhisperSubmitVO>> submitWhisperTask(@RequestHeader("from-source") String fromSource,
                                                            @Validated @RequestBody WhisperDTO whisperDTO) {
        return whisperService.submitWhisper(whisperDTO)
                .flatMap(whisperSubmitVO -> Mono.just(ResUtil.success(whisperSubmitVO)));
    }


    @GetMapping("/status/{taskId}")
    public Flux<ServerSentEvent<WhisperTaskStatusVOV1>> taskStatus(@PathVariable("taskId") Long taskId,
                                                                 @Validated @NotNull(message = "Token不能为空") @RequestHeader("accessToken") String accessToken) {
        log.info("TEST");
        Flux<ServerSentEvent<WhisperTaskStatusVOV1>>  heartbeatFlux = Flux.interval(Duration.ofSeconds(10))
        .map(tick -> {
            log.info("whisper task status taskId = {}, HEARTBEAT", taskId);
            return ServerSentEvent.<WhisperTaskStatusVOV1>builder()
                    .id(String.valueOf(System.currentTimeMillis()))
                    .event("heartbeat")
                    .build();
        });
        return Flux.merge(heartbeatFlux, whisperService.whisperTaskStatusV1(WhisperTaskQueryParam.builder()
                .whisperTaskId(taskId).accessToken(accessToken)
                .build())
                .flatMap(status -> Flux.just(ServerSentEvent.<WhisperTaskStatusVOV1>builder(status)
                        .id(String.valueOf(System.currentTimeMillis()))
                        .event("message")
                        .build())));
    }


}
