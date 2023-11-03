package com.anynote.note.listener;

import com.alibaba.fastjson2.JSON;
import com.anynote.common.rocketmq.tags.NoteTaskTagsEnum;
import com.anynote.note.api.model.po.NoteTaskOperationHistory;
import com.anynote.note.mapper.NoteTaskOperationHistoryMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 笔记任务监听器
 *
 * @author 称霸幼儿园
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.note-task-topic}",
        consumerGroup = "${anynote.data.rocketmq.note-task-group}")
public class NoteTaskMessageListener implements RocketMQListener<MessageExt> {

    @Autowired
    private NoteTaskOperationHistoryMapper noteTaskOperationHistoryMapper;

    @Override
    public void onMessage(MessageExt messageExt) {
        if (NoteTaskTagsEnum.valueOf(messageExt.getTags()) == NoteTaskTagsEnum.INSERT_HISTORY) {
            log.info("插入记录---------------------------------------------------------------------------------------------------");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                noteTaskOperationHistoryMapper.insert(objectMapper.readValue(new String(messageExt.getBody()), NoteTaskOperationHistory.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
//        if (NoteTaskTagsEnum.valueOf(messageExt.getTags()) == NoteTaskTagsEnum.SUBMIT) {
//            log.info("收到提交笔记任务");
//        }
    }
}
