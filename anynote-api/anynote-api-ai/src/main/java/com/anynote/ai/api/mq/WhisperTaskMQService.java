package com.anynote.ai.api.mq;

import com.anynote.ai.api.model.bo.WhisperTaskStatusUpdatedMQParamV1;
import com.anynote.common.rocketmq.callback.RocketmqSendCallbackBuilder;
import com.anynote.common.rocketmq.properties.RocketMQProperties;
import com.anynote.common.rocketmq.tags.WhisperTagsEnum;
import com.google.gson.Gson;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Whisper 任务消息队列
 * @author 称霸幼儿园
 */
@Component
public class WhisperTaskMQService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private RocketMQProperties rocketMQProperties;

    public void sendWhisperTaskStatusUpdateMessage(WhisperTaskStatusUpdatedMQParamV1 mqParamV1) {
        String WHISPER_TASK_STATUS_UPDATE_DESTINATION =
                rocketMQProperties.getAiChatTopic() + ":" + WhisperTagsEnum.WHISPER_TASK_STATUS_UPDATED.name();
        rocketMQTemplate.asyncSendOrderly(
                WHISPER_TASK_STATUS_UPDATE_DESTINATION,
                new Gson().toJson(mqParamV1),
                String.valueOf(mqParamV1.getTaskId()),
                RocketmqSendCallbackBuilder.commonCallback());
    }

}
