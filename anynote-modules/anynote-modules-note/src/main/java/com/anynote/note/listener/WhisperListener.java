package com.anynote.note.listener;

import com.anynote.ai.api.enums.WhisperTaskStatus;
import com.anynote.ai.api.model.bo.WhisperTaskStatusUpdatedMQParamV1;
import com.anynote.common.redis.constant.RedisKey;
import com.anynote.common.redis.service.RedisService;
import com.anynote.common.rocketmq.tags.WhisperTagsEnum;
import com.anynote.core.utils.StringUtils;
import com.anynote.note.model.bo.MoocAsrTaskInfo;
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

@Slf4j
@Component
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.ai-chat-topic}",
        consumerGroup = "${anynote.data.rocketmq.note-whisper-task-group}", maxReconsumeTimes = 5,selectorType = SelectorType.TAG,
        selectorExpression = "WHISPER_TASK_STATUS_UPDATED",
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.ORDERLY
)
public class WhisperListener implements RocketMQListener<MessageExt> {

    @Resource
    private RedisService redisService;

    @Resource
    private Gson gson;

    @Override
    public void onMessage(MessageExt messageExt) {
        if (WhisperTagsEnum.WHISPER_TASK_STATUS_UPDATED.equals(WhisperTagsEnum.valueOf(messageExt.getTags()))) {
            String body = new String(messageExt.getBody());
            log.info("WHISPER_TASK_STATUS_UPDATED{}", body);
            this.onWhisperTaskStatusUpdate(gson.fromJson(body, WhisperTaskStatusUpdatedMQParamV1.class));
        }
    }

    private void onWhisperTaskStatusUpdate(WhisperTaskStatusUpdatedMQParamV1 updatedMQParamV1) {
        WhisperTaskStatus status = updatedMQParamV1.getStatus();
        String key = StringUtils.format(RedisKey.MOOC_ASR_TASK, updatedMQParamV1.getTaskId());
        MoocAsrTaskInfo moocAsrTaskInfo = redisService.getCacheObject(key);
        moocAsrTaskInfo.setTaskStatus(status);
        redisService.setCacheObject(key, moocAsrTaskInfo);
        redisService.setCacheObject(StringUtils.format(RedisKey.MOOC_ASR_TASK_MOOC_ID_AND_MOOC_ITEM_ID_KEY,
                moocAsrTaskInfo.getMoocId(),
                moocAsrTaskInfo.getMoocItemId()), moocAsrTaskInfo);
        log.info("Whisper Task id={}，状态更新：{}", updatedMQParamV1.getTaskId(), status.name());
//        switch (status) {
//            case SUCCESS: {
//                log.info(updatedMQParamV1.getResult().getSrt());
//                break;
//            }
//        }
    }


}
