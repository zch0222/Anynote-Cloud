package com.anynote.note.listener;

import com.anynote.common.rocketmq.tags.NoteTaskTagsEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 笔记任务监听器
 *
 * @author 称霸幼儿园
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${anynote.note.data.rocketmq.note-task-topic}",
        consumerGroup = "${anynote.note.data.rocketmq.note-task-group}")
public class NoteTaskMessageListener implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        if (NoteTaskTagsEnum.valueOf(messageExt.getTags()) == NoteTaskTagsEnum.SUBMIT) {
            log.info("收到提交笔记任务");
        }
    }
}
