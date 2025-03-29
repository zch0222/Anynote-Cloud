package com.anynote.file.listener;

import com.anynote.ai.api.enums.WhisperTaskStatus;
import com.anynote.ai.api.model.bo.WhisperTaskStatusUpdatedMQParamV1;
import com.anynote.ai.api.mq.WhisperTaskMQService;
import com.anynote.common.rocketmq.callback.RocketmqSendCallbackBuilder;
import com.anynote.common.rocketmq.properties.RocketMQProperties;
import com.anynote.common.rocketmq.tags.WhisperTagsEnum;
import com.anynote.core.constant.FileConstants;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.RemoteResDataUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.file.service.FileService;
import com.anynote.note.api.RemoteMoocService;
import com.anynote.note.api.model.dto.MoocAsrInfoUpdateDTO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.ai-chat-topic}",
        consumerGroup = "${anynote.data.rocketmq.file-whisper-task-group}",
        maxReconsumeTimes = 1,selectorType = SelectorType.TAG,
        selectorExpression = "WHISPER_TASK_STATUS_UPDATED",
        consumeMode = ConsumeMode.ORDERLY,
        messageModel = MessageModel.CLUSTERING)
public class WhisperListener implements RocketMQListener<MessageExt> {

    @Resource
    private FileService fileService;

    @Resource
    private Gson gson;

    @Resource
    private RemoteMoocService remoteMoocService;

    @Resource
    private RocketMQProperties rocketMQProperties;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private WhisperTaskMQService whisperTaskMQService;

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
        switch (status) {
            case UPLOADING_SRT_OBJECT: {
                log.info(updatedMQParamV1.getResult().getSrt());
                String objectName = null;
                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(updatedMQParamV1.getResult().getSrt()
                        .getBytes(StandardCharsets.UTF_8))) {
                    objectName = fileService.upload(byteArrayInputStream, updatedMQParamV1.getResult().getSrt().length(),
                            StringUtils.format(FileConstants.MOOC_SRT_PATH_TEMPLATE,
                                    UUID.randomUUID().toString().replace("-", ""), updatedMQParamV1.getTaskId()));
                } catch (Exception e) {
                    whisperTaskMQService.sendWhisperTaskStatusUpdateMessage(WhisperTaskStatusUpdatedMQParamV1
                            .builder().status(WhisperTaskStatus.FAILED)
                            .taskId(updatedMQParamV1.getTaskId())
                            .build());
                    throw new RuntimeException(e);
                }
                log.info("上传字幕对象：\"{}\"成功", objectName);
                String destination = rocketMQProperties.getAiChatTopic() + ":" + WhisperTagsEnum.WHISPER_TASK_STATUS_UPDATED.name();
                try {
                    RemoteResDataUtil.getResData(remoteMoocService.updateAsrInfo(MoocAsrInfoUpdateDTO.builder()
                            .taskId(updatedMQParamV1.getTaskId())
                            .srtObjectName(objectName)
                            .build(), "inner"));
                    whisperTaskMQService.sendWhisperTaskStatusUpdateMessage(WhisperTaskStatusUpdatedMQParamV1
                            .builder().status(WhisperTaskStatus.SUCCESS)
                            .taskId(updatedMQParamV1.getTaskId())
                            .build());
//                    rocketMQTemplate.asyncSend(destination, gson.toJson(WhisperTaskStatusUpdatedMQParamV1
//                                    .builder().status(WhisperTaskStatus.SUCCESS)
//                                    .taskId(updatedMQParamV1.getTaskId())
//                                    .build()),
//                            RocketmqSendCallbackBuilder.commonCallback());
                } catch (BusinessException e) {
                    log.error(e.getErrorMessage(), e);
                    whisperTaskMQService.sendWhisperTaskStatusUpdateMessage(WhisperTaskStatusUpdatedMQParamV1
                            .builder().status(WhisperTaskStatus.FAILED)
                            .taskId(updatedMQParamV1.getTaskId())
                            .build());
//                    rocketMQTemplate.asyncSend(destination, gson.toJson(WhisperTaskStatusUpdatedMQParamV1
//                                    .builder().status(WhisperTaskStatus.FAILED)
//                                    .taskId(updatedMQParamV1.getTaskId())
//                                    .build()),
//                            RocketmqSendCallbackBuilder.commonCallback());
                }
                break;
            }
        }
    }


}
