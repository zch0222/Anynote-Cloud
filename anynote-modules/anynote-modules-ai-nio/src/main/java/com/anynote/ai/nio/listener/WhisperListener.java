package com.anynote.ai.nio.listener;

import com.anynote.ai.api.model.bo.WhisperTaskCreatedMQParam;
import com.anynote.ai.api.model.bo.WhisperTaskStatusUpdatedMQParam;
import com.anynote.ai.api.model.po.WhisperTask;
import com.anynote.ai.api.model.po.WhisperTaskLog;
import com.anynote.ai.api.model.po.WhisperTaskText;
import com.anynote.ai.nio.constants.WhisperConstants;
import com.anynote.ai.api.model.vo.WhisperSubmitVO;
import com.anynote.ai.api.model.vo.WhisperTaskStatusVO;
import com.anynote.ai.nio.service.WhisperTaskLogService;
import com.anynote.ai.nio.service.WhisperTaskService;
import com.anynote.ai.nio.service.WhisperTaskTextService;
import com.anynote.common.redis.constant.RedisChannel;
import com.anynote.common.redis.model.bo.RedisMessage;
import com.anynote.common.redis.service.ConfigService;
import com.anynote.common.redis.service.RedisService;
import com.anynote.common.rocketmq.callback.RocketmqSendCallbackBuilder;
import com.anynote.common.rocketmq.properties.RocketMQProperties;
import com.anynote.common.rocketmq.tags.WhisperTagsEnum;
import com.anynote.core.constant.RedisConstants;
import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.note-topic}",
        consumerGroup = "${anynote.data.rocketmq.whisper-group}", maxReconsumeTimes = 5,selectorType = SelectorType.TAG,
        selectorExpression = "WHISPER_TASK_FINISHED || WHISPER_TASK_STATUS_UPDATED || WHISPER_TASK_SUBMITTED",
        messageModel = MessageModel.CLUSTERING)
public class WhisperListener implements RocketMQListener<MessageExt> {

    @Resource
    private RedisService redisService;

    @Resource
    private Gson gson;

    @Resource
    private WebClient webClient;

    @Resource
    private ConfigService configService;

    @Resource
    private RocketMQProperties rocketMQProperties;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private WhisperTaskLogService whisperTaskLogService;

    @Resource
    private WhisperTaskService whisperTaskService;

    @Resource
    private WhisperTaskTextService whisperTaskTextService;


    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("TAG: " + messageExt.getTags());
        if (WhisperTagsEnum.WHISPER_TASK_FINISHED.equals(WhisperTagsEnum.valueOf(messageExt.getTags()))) {

        }
        else if (WhisperTagsEnum.WHISPER_TASK_STATUS_UPDATED.equals(WhisperTagsEnum.valueOf(messageExt.getTags()))) {
            String body = new String(messageExt.getBody());
            log.info("WHISPER_TASK_STATUS_UPDATED{}", body);
            this.onWhisperTaskStatusUpdate(gson.fromJson(body, WhisperTaskStatusUpdatedMQParam.class));
        }
        else if (WhisperTagsEnum.WHISPER_TASK_SUBMITTED.equals(WhisperTagsEnum.valueOf(messageExt.getTags()))) {
            String body = new String(messageExt.getBody());
            log.info("WHISPER_TASK_SUBMITTED:{}", body);
            this.onWhisperTaskSubmitted(gson.fromJson(body, WhisperTaskCreatedMQParam.class));
        }
    }

    private void onWhisperTaskStatusUpdate(WhisperTaskStatusUpdatedMQParam whisperTaskStatusUpdatedMQParam) {
//        RedisMessage[] redisMessages = {
//                RedisMessage.builder()
//                        .channel(RedisChannel.WHISPER_TASK_CHANNEL + whisperTaskStatusVO.getTaskId())
//                        .message(gson.toJson(whisperTaskStatusVO))
//                        .build()
//        };
//        redisService.batchPublish(Arrays.asList(redisMessages));
        WhisperTaskStatusVO whisperTaskStatusVO = whisperTaskStatusUpdatedMQParam.getWhisperTaskStatusVO();
        boolean isFinished = WhisperTaskStatusVO.Status.FINISHED.equals(WhisperTaskStatusVO.Status.valueOf(whisperTaskStatusVO.getStatus()));
        WhisperTaskCreatedMQParam whisperTaskCreatedMQParam = redisService
                .getCacheObject(RedisConstants.WHISPER_TASK_INFO +
                        whisperTaskStatusUpdatedMQParam.getWhisperTaskStatusVO().getTaskId());
        String whisperTaskLockKey = RedisConstants.WHISPER_TASK_LOG_LOCK_KEY + whisperTaskCreatedMQParam
                .getWhisperTaskId() + ":STATUS:" + whisperTaskStatusVO.getStatus();
        Date now = new Date();
        if (redisService.setNX(whisperTaskLockKey, "")) {
            redisService.expire(whisperTaskLockKey, 3600);
            whisperTaskLogService.getBaseMapper().insert(WhisperTaskLog.builder()
                    .whisperTaskId(whisperTaskCreatedMQParam.getWhisperTaskId())
                    .taskStatus(whisperTaskStatusUpdatedMQParam.getWhisperTaskStatusVO().getStatus())
                    .userId(whisperTaskCreatedMQParam.getUserId())
                    .deleted(0)
                    .updateTime(now)
                    .createTime(now).build());
        }
        if (isFinished) {
            whisperTaskTextService.getBaseMapper().insert(WhisperTaskText
                    .builder()
                    .whisperTaskId(whisperTaskCreatedMQParam.getWhisperTaskId())
                    .whisperText(whisperTaskStatusVO.getResult().getText())
                    .updateBy(whisperTaskCreatedMQParam.getUserId())
                    .updateTime(now)
                    .createBy(whisperTaskCreatedMQParam.getUserId())
                    .deleted(0)
                    .createTime(now)
                    .build());
        }
        whisperTaskService.getBaseMapper().updateById(WhisperTask.builder()
                .id(whisperTaskCreatedMQParam.getWhisperTaskId())
                .taskStatus(WhisperTaskStatusVO.Status.valueOf(whisperTaskStatusUpdatedMQParam.getWhisperTaskStatusVO().getStatus()).getValue())
                        .srtObjectName(isFinished ? whisperTaskStatusVO.getResult().getSrt() : "")
                        .srtObjectName(isFinished ? whisperTaskStatusVO.getResult().getTxt() : "")
                .updateTime(now)
                .build());
        List<RedisMessage> messageList = new ArrayList<>(1);
        messageList.add(RedisMessage.builder()
                .channel(RedisChannel.WHISPER_TASK_STATUS_CHANNEL + whisperTaskCreatedMQParam.getWhisperTaskId())
                        .message(gson.toJson(whisperTaskStatusVO))
                .build());
        redisService.batchPublish(messageList);
    }

    private void onWhisperTaskSubmitted(WhisperTaskCreatedMQParam whisperTaskCreatedMQParam) {
        WhisperSubmitVO whisperSubmitVO = whisperTaskCreatedMQParam.getWhisperSubmitVO();
        redisService.setCacheObject(RedisConstants.WHISPER_TASK_INFO + whisperSubmitVO.getTaskId(), whisperTaskCreatedMQParam);
//        webClient.get()
//                .uri(aiServerAddress + WhisperConstants.WHISPER_TASK_STATUS_URL + whisperSubmitVO.getTaskId())
//                .retrieve()
//                .bodyToFlux(WhisperTaskStatusVO.class)
//                .subscribeOn(Schedulers.boundedElastic())
//                .subscribe(whisperTaskStatusVO -> {
//                    log.info(gson.toJson(whisperTaskStatusVO));
//                    Date now = new Date();
//                    if (WhisperTaskStatusVO.Type.STATUS_UPDATE
//                            .equals(WhisperTaskStatusVO.Type.valueOf(whisperTaskStatusVO.getType()))) {
//                        String destination = rocketMQProperties.getNoteTopic() + ":" + WhisperTagsEnum.WHISPER_TASK_STATUS_UPDATED.name();
//                        rocketMQTemplate.asyncSend(destination, gson.toJson(WhisperTaskStatusUpdatedMQParam.builder()
//                                        .whisperTaskStatusVO(whisperTaskStatusVO)
//                                        .userId(whisperTaskCreatedMQParam.getUserId())
//                                        .whisperTaskId(whisperTaskCreatedMQParam.getWhisperTaskId())
//                                        .createTime(now)
//                                        .updateTime(now).build()),
//                                RocketmqSendCallbackBuilder.commonCallback());
//                    }
//                });
        log.info("onWhisperTaskSubmitted RocketMQ task finished");
    }
}
