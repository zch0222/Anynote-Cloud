package com.anynote.ai.nio.listener;

import com.anynote.ai.api.enums.WhisperTaskStatus;
import com.anynote.ai.api.model.bo.WhisperTaskCreatedMQParam;
import com.anynote.ai.api.model.bo.WhisperTaskStatusUpdatedMQParam;
import com.anynote.ai.api.model.bo.WhisperTaskStatusUpdatedMQParamV1;
import com.anynote.ai.api.model.po.WhisperTask;
import com.anynote.ai.nio.service.WhisperService;
import com.anynote.ai.nio.service.WhisperTaskService;
import com.anynote.common.rocketmq.tags.WhisperTagsEnum;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.ai-chat-topic}",
        consumerGroup = "${anynote.data.rocketmq.ai-chat-group}", maxReconsumeTimes = 5,selectorType = SelectorType.TAG,
        selectorExpression = "WHISPER_TASK_FINISHED || WHISPER_TASK_STATUS_UPDATED || WHISPER_TASK_SUBMITTED",
        messageModel = MessageModel.CLUSTERING)
public class WhisperListenerV1 implements RocketMQListener<MessageExt> {

    @Resource
    private Gson gson;

    @Resource
    private WhisperTaskService whisperTaskService;

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
