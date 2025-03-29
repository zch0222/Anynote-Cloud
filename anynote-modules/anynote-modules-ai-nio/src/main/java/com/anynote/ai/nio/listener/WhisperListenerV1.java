package com.anynote.ai.nio.listener;

import com.anynote.ai.api.enums.WhisperTaskStatus;
import com.anynote.ai.api.model.bo.WhisperTaskCreatedMQParam;
import com.anynote.ai.api.model.bo.WhisperTaskStatusUpdatedMQParam;
import com.anynote.ai.api.model.bo.WhisperTaskStatusUpdatedMQParamV1;
import com.anynote.ai.api.model.po.WhisperTask;
import com.anynote.ai.nio.model.vo.WhisperTaskStatusVOV1;
import com.anynote.ai.nio.service.WhisperService;
import com.anynote.ai.nio.service.WhisperTaskService;
import com.anynote.common.redis.constant.RedisChannel;
import com.anynote.common.redis.model.bo.RedisMessage;
import com.anynote.common.redis.service.RedisService;
import com.anynote.common.rocketmq.tags.WhisperTagsEnum;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.ai-chat-topic}",
        consumerGroup = "${anynote.data.rocketmq.ai-chat-whisper-task-group}", maxReconsumeTimes = 5,selectorType = SelectorType.TAG,
        selectorExpression = "WHISPER_TASK_FINISHED || WHISPER_TASK_STATUS_UPDATED || WHISPER_TASK_SUBMITTED",
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.ORDERLY
)
public class WhisperListenerV1 implements RocketMQListener<MessageExt> {

    @Resource
    private Gson gson;

    @Resource
    private WhisperTaskService whisperTaskService;

    @Resource
    private RedisService redisService;

    @Override
    public void onMessage(MessageExt messageExt) {
        if (WhisperTagsEnum.WHISPER_TASK_FINISHED.equals(WhisperTagsEnum.valueOf(messageExt.getTags()))) {

        }
        else if (WhisperTagsEnum.WHISPER_TASK_STATUS_UPDATED.equals(WhisperTagsEnum.valueOf(messageExt.getTags()))) {
            String body = new String(messageExt.getBody());
            log.info("WHISPER_TASK_STATUS_UPDATED{}", body);
            this.onWhisperTaskStatusUpdate(gson.fromJson(body, WhisperTaskStatusUpdatedMQParamV1.class));
        }
        else if (WhisperTagsEnum.WHISPER_TASK_SUBMITTED.equals(WhisperTagsEnum.valueOf(messageExt.getTags()))) {

        }
    }

    private void onWhisperTaskStatusUpdate(WhisperTaskStatusUpdatedMQParamV1 updatedMQParamV1) {

        WhisperTaskStatus status = updatedMQParamV1.getStatus();
        List<RedisMessage> messageList = new ArrayList<>(1);
        messageList.add(RedisMessage.builder()
                .channel( RedisChannel.WHISPER_TASK_STATUS_CHANNEL + updatedMQParamV1.getTaskId())
                .message(gson.toJson(WhisperTaskStatusVOV1.builder()
                        .whisperTaskStatus(updatedMQParamV1.getStatus().getValue())
                        .taskId(updatedMQParamV1.getTaskId())
                        .build()))
                .build());
        redisService.batchPublish(messageList);
        WhisperTask whisperTask = WhisperTask.builder()
                .id(updatedMQParamV1.getTaskId())
                .taskStatus(status.getValue())
                .build();
        whisperTaskService.updateById(whisperTask);
//        switch (status) {
//            case STARTING:
//                break;
//            case CONVERTING: {
//                break;
//            }
//            case SUCCESS: {
//                break;
//            }
//            case FAILED: {
//
//            }
//        }

    }
}
