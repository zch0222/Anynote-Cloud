package com.anynote.note.listener;

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
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.canal-topic}",
        consumerGroup = "${anynote.data.rocketmq.canal-mooc-group}", maxReconsumeTimes = 5,
        messageModel = MessageModel.CLUSTERING
)
public class CanalMoocListener implements RocketMQListener<MessageExt> {

    @Resource
    private Gson gson;

    @Override
    public void onMessage(MessageExt messageExt) {
        log.info(gson.toJson(messageExt));
        log.info(new String(messageExt.getBody(), StandardCharsets.UTF_8));
    }
}
