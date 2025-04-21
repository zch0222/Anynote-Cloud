package com.anynote.ai.nio.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.anynote.ai.api.enums.WhisperTaskStatus;
import com.anynote.ai.api.model.bo.WhisperTaskStatusUpdatedMQParamV1;
import com.anynote.ai.api.model.po.WhisperTask;
import com.anynote.ai.api.model.vo.WhisperTaskStatusVO;
import com.anynote.ai.api.mq.WhisperTaskMQService;
import com.anynote.ai.nio.datascope.annotation.RequiresWhisperTaskPermissions;
import com.anynote.ai.nio.model.bo.WhisperConfig;
import com.anynote.ai.nio.model.bo.WhisperTaskQueryParam;
import com.anynote.ai.api.model.dto.WhisperDTO;
import com.anynote.ai.api.model.vo.WhisperSubmitVO;
import com.anynote.ai.nio.model.vo.WhisperTaskStatusVOV1;
import com.anynote.ai.nio.model.vo.WhisperVO;
import com.anynote.ai.nio.service.FfmpegService;
import com.anynote.ai.nio.service.WhisperService;
import com.anynote.ai.nio.service.WhisperTaskService;
import com.anynote.common.redis.constant.RedisChannel;
import com.anynote.common.redis.service.ConfigService;
import com.anynote.common.rocketmq.callback.RocketmqSendCallbackBuilder;
import com.anynote.common.rocketmq.properties.RocketMQProperties;
import com.anynote.common.rocketmq.tags.WhisperTagsEnum;
import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.constant.SysApiStatisticsType;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.RemoteResDataUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.file.api.RemoteFileService;
import com.anynote.file.api.model.dto.DownloadObjectDTO;
import com.anynote.system.api.RemoteSysApiStatisticsService;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.dto.IncreaseApiUsageDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.google.gson.Gson;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class WhisperServiceImpl implements WhisperService {


    @Resource
    private ReactiveRedisTemplate<String, JSONObject> reactiveRedisTemplate;

    @Resource
    private ConfigService configService;

    @Resource
    private WebClient webClient;

    @Resource
    private Gson gson;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private RocketMQProperties rocketMQProperties;

    @Resource
    private TokenUtil tokenUtil;

    @Resource
    private WhisperTaskService whisperTaskService;

    @Resource
    private Executor whisperExecutor;

    @Resource
    private Executor ffmpegExecutor;

    @Resource
    private Executor ioExecutor;

    @Resource
    private RemoteFileService remoteFileService;

    @Resource
    private FfmpegService ffmpegService;

    @Resource
    private WhisperTaskMQService whisperTaskMQService;

    @Resource
    private RemoteSysApiStatisticsService remoteSysApiStatisticsService;



    @Override
    public Flux<ServerSentEvent<String>> whisper(WhisperDTO whisperDTO) {
//        Flux<ServerSentEvent<String>> heartbeatFlux = Flux.interval(Duration.ofSeconds(10))
//                .map(tick -> {
//                    log.info("heartbeat");
//                    return ServerSentEvent.<String>builder()
//                            .id(new Date().toString())
//                            .data("heartbeat")
//                            .event("heartbeat")
//                            .build();
//                });

        String aiServiceAddress = configService.getAIServerAddress();
//        return webClient.post()
//                .uri(aiServiceAddress + "/api/whisper/submit")
//                .body(Mono.just(whisperDTO), WhisperDTO.class)
//                .retrieve()
//                .bodyToMono(WhisperSubmitVO.class)
//                .flux()
//                .flatMap(whisperSubmitVO ->
//                    Flux.merge(heartbeatFlux, reactiveRedisTemplate
//                        .listenToChannel(RedisChannel.WHISPER_TASK_CHANNEL + whisperSubmitVO.getTaskId())
//                        .map(value -> {
//                            log.info(value.toString());
//                            return ServerSentEvent.<String>builder()
//                                    .id(String.valueOf(System.currentTimeMillis()))
//                                    .data(gson.toJson(value.getMessage()))
//                                    .event("message")
//                                    .build();
//                        }))
//                );
//        return Flux.merge(heartbeatFlux);

        return webClient.post()
                .uri(aiServiceAddress + "/api/whisper")
                .body(Mono.just(whisperDTO), WhisperDTO.class)
                .retrieve()
                .bodyToFlux(WhisperVO.class)
                .flatMap(whisperVO -> {
                    log.info(gson.toJson(whisperVO));
                    return Flux.just(ServerSentEvent
                            .<String>builder()
                            .id(String.valueOf(System.currentTimeMillis()))
                            .event("message")
                            .data(gson.toJson(whisperVO)).build());
                });
    }

    private String ffmpeg() {
        log.info("FFMPEG");
        return "FFMPEG";
    }

    private void whisper(String s) {
        log.info("whisper----" + s);
    }

    /**
     * 记录whisper调用次数
     */
    public void increaseWhisperUsageCount() {
        try {
            Date now = new Date();
            ioExecutor.execute(() -> {
                RemoteResDataUtil.getResData(remoteSysApiStatisticsService.increaseUsage(IncreaseApiUsageDTO.builder()
                        .time(now)
                        .type(SysApiStatisticsType.WHISPER)
                        .build(), SecurityConstants.INNER));
            });
        } catch (Exception e) {
            log.error("记录Whisper调用次数失败", e);
        }
    }

    /**
     * 调用远程Whisper服务
     */
    private String remoteWhisper(String audioPath, String language) {
        increaseWhisperUsageCount();
        WhisperConfig whisperConfig = gson.fromJson(configService.getWhisperConfig(), WhisperConfig.class);
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new FileSystemResource(audioPath));
        builder.part("language", "zh");
        builder.part("model", "whisper-1");
        builder.part("response_format", "vtt");
        return webClient.post()
                .uri(whisperConfig.getBaseUrl() + "/audio/transcriptions")
                .header("Authorization", StringUtils.format("Bearer {}", whisperConfig.getApiKey()))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(builder.build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public void whisperV1(WhisperDTO whisperDTO, Long taskId) {
        WhisperConfig whisperConfig = gson.fromJson(configService.getWhisperConfig(), WhisperConfig.class);
        CompletableFuture.supplyAsync(() -> {
            log.info("whisper object: {}, language: {}, start download",
                    whisperDTO.getObjectName(), whisperDTO.getLanguage());
            return RemoteResDataUtil.getResData(remoteFileService
                    .downloadObject(DownloadObjectDTO.builder()
                            .objectName(whisperDTO.getObjectName())
                            .fileFolder(whisperConfig.getTmpFileFolder())
                            .build(), "inner"));
        }, whisperExecutor)
        .thenApplyAsync(filePath -> {
            log.info("whisper object: {}, language: {}, start ffmpeg",
                    whisperDTO.getObjectName(), whisperDTO.getLanguage());
            return ffmpegService.copyAudio(filePath);
        }, ffmpegExecutor).thenAcceptAsync(audioPath -> {
            log.info("whisper object: {}, language: {}, start remote whisper",
                    whisperDTO.getObjectName(), whisperDTO.getLanguage());
            String srt = remoteWhisper(audioPath, whisperDTO.getLanguage());
            log.info(srt);
            whisperTaskMQService.sendWhisperTaskStatusUpdateMessage(WhisperTaskStatusUpdatedMQParamV1
                    .builder().status(WhisperTaskStatus.UPLOADING_SRT_OBJECT)
                    .taskId(taskId)
                    .result(WhisperTaskStatusUpdatedMQParamV1.Result.builder()
                            .srt(srt)
                            .build())
                    .build());
//            String destination = rocketMQProperties.getAiChatTopic() + ":" + WhisperTagsEnum.WHISPER_TASK_STATUS_UPDATED.name();
//            rocketMQTemplate.asyncSend(destination, gson.toJson(WhisperTaskStatusUpdatedMQParamV1
//                            .builder().status(WhisperTaskStatus.UPLOADING_SRT_OBJECT)
//                            .taskId(taskId)
//                            .result(WhisperTaskStatusUpdatedMQParamV1.Result.builder()
//                                    .srt(srt)
//                                    .build())
//                            .build()),
//                    RocketmqSendCallbackBuilder.commonCallback());
        }, whisperExecutor)
        .exceptionally(ex -> {
            log.error("whisper object: {}, language: {}, error",
                    whisperDTO.getObjectName(), whisperDTO.getLanguage(), ex);
            whisperTaskMQService.sendWhisperTaskStatusUpdateMessage(WhisperTaskStatusUpdatedMQParamV1
                    .builder().status(WhisperTaskStatus.FAILED)
                    .taskId(taskId)
                    .errorMessage(ex.getMessage())
                    .build());
//            String destination = rocketMQProperties.getAiChatTopic() + ":" + WhisperTagsEnum.WHISPER_TASK_STATUS_UPDATED.name();
//            rocketMQTemplate.asyncSend(destination, gson.toJson(WhisperTaskStatusUpdatedMQParamV1
//                            .builder().status(WhisperTaskStatus.FAILED)
//                            .taskId(taskId)
//                            .errorMessage(ex.getMessage())
//                            .build()),
//                    RocketmqSendCallbackBuilder.commonCallback());
            throw new BusinessException(StringUtils.format("whisper object: {}, language: {}, error",
                    whisperDTO.getObjectName(), whisperDTO.getLanguage()));
        });
    }

    @Override
    public Mono<WhisperSubmitVO> submitWhisper(WhisperDTO whisperDTO) {
        return Mono.deferContextual(ctx -> {
            Date now = new Date();
            log.info(gson.toJson(whisperDTO));
            LoginUser loginUser = tokenUtil.getLoginUser(ctx.get(SecurityConstants.ACCESS_TOKEN));
            WhisperTask whisperTask = WhisperTask.builder()
                    .createBy(loginUser.getUserId())
                    .updateBy(loginUser.getUserId())
                    .createTime(now)
                    .updateTime(now)
                    .taskStatus(0)
                    .build();
            whisperTaskService.getBaseMapper().insert(whisperTask);
            whisperV1(whisperDTO, whisperTask.getId());
            return Mono.just(WhisperSubmitVO.builder().taskId(whisperTask.getId()).build());
        }).subscribeOn(Schedulers.boundedElastic());
    }


    @RequiresWhisperTaskPermissions
    @Override
    public Flux<ServerSentEvent<WhisperTaskStatusVO>> whisperTaskStatus(WhisperTaskQueryParam queryParam) {

//        AtomicBoolean isListened = new AtomicBoolean(false);
        ReentrantLock listenLock = new ReentrantLock();
        Condition condition = listenLock.newCondition();
        AtomicBoolean isListened = new AtomicBoolean(false);
        Flux<ServerSentEvent<WhisperTaskStatusVO>> heartbeatFlux = Flux.interval(Duration.ofSeconds(10))
                .map(tick -> {
                    log.info("heartbeat");
                    return ServerSentEvent.<WhisperTaskStatusVO>builder()
                            .id(new Date().toString())
                            .event("heartbeat")
                            .build();
                });
        Flux<ServerSentEvent<WhisperTaskStatusVO>> statusFlux = Mono.fromCallable(() -> {
            listenLock.lock();
            try {
                while (!isListened.get()) {
                    condition.await();
                }
                log.info("query mysql");
                return whisperTaskService
                        .getBaseMapper().selectById(queryParam.getWhisperTaskId());
            } finally {
                listenLock.unlock();
            }
        }).flux()
                .flatMap(whisperTask -> {
                    log.info(gson.toJson(whisperTask));
                    return Flux.just(ServerSentEvent
                            .builder(WhisperTaskStatusVO.builder()
                                    .status(WhisperTaskStatusVO.Status.values()[whisperTask.getTaskStatus()].name())
                                    .result(WhisperTaskStatusVO.WhisperTaskResult.builder()
                                            .srt(whisperTask.getSrtObjectName())
                                            .txt(whisperTask.getTxtObjectName()).build())
                                    .build())
                            .event("message")
                            .id(String.valueOf(System.currentTimeMillis())).build());
                })
                .subscribeOn(Schedulers.boundedElastic());

        String chanel = RedisChannel.WHISPER_TASK_STATUS_CHANNEL + queryParam.getWhisperTaskId();
        Flux<ServerSentEvent<WhisperTaskStatusVO>> redisSubFlux = reactiveRedisTemplate.listenToChannel(chanel)
                .map(message -> {
                    log.info(String.valueOf(message));
                    return ServerSentEvent
                            .builder(message.getMessage().toJavaObject(WhisperTaskStatusVO.class))
                            .build();
                }).doOnSubscribe(subscription -> {
                    listenLock.lock();
                    try {
                        isListened.set(true);
                        condition.signal();
                        log.info("chanel {} is listening", chanel);
                    } finally {
                        listenLock.unlock();
                    }
                });
        return Flux.merge(heartbeatFlux, redisSubFlux, statusFlux).takeUntil(sse -> StringUtils.isNotNull(sse.data()) &&
                WhisperTaskStatusVO.Status.FINISHED.name().equals(sse.data().getStatus()));
    }

    @RequiresWhisperTaskPermissions
    @Override
    public Flux<WhisperTaskStatusVOV1> whisperTaskStatusV1(WhisperTaskQueryParam queryParam) {
        LoginUser loginUser = tokenUtil.getLoginUser(queryParam.getAccessToken());
        String chanel = RedisChannel.WHISPER_TASK_STATUS_CHANNEL + queryParam.getWhisperTaskId();
//        Flux<ServerSentEvent<WhisperTaskStatusVOV1>>  heartbeatFlux = Flux.interval(Duration.ofSeconds(10))
//                .map(tick -> {
//                    log.info("whisper task status taskId = {}, HEARTBEAT", queryParam.getWhisperTaskId());
//                    return ServerSentEvent.<WhisperTaskStatusVOV1>builder()
//                            .id(String.valueOf(System.currentTimeMillis()))
//                            .event("heartbeat")
//                            .build();
//                });
        Flux<WhisperTaskStatusVOV1> redisSubFlux = reactiveRedisTemplate.listenToChannel(chanel)
                .map(message -> message.getMessage().toJavaObject(WhisperTaskStatusVOV1.class));
        Flux<WhisperTaskStatusVOV1> mysqlFlux = Mono.fromCallable(() -> {
            return whisperTaskService
                    .getBaseMapper().selectById(queryParam.getWhisperTaskId());
        }).flux().flatMap(whisperTask -> {
            log.info("whisper task status taskId = {}, MYSQL", queryParam.getWhisperTaskId());
            return Flux.just(WhisperTaskStatusVOV1.builder()
                    .taskId(whisperTask.getId())
                    .whisperTaskStatus(whisperTask.getTaskStatus())
                    .build());
        });
        return Flux.mergeSequential(mysqlFlux, redisSubFlux);
    }
}
