package com.anynote.note.listener;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.alibaba.fastjson2.JSON;
import com.anynote.common.elasticsearch.constant.ElasticsearchIndexConstants;
import com.anynote.common.elasticsearch.model.EsNoteIndex;
import com.anynote.common.rocketmq.tags.NoteTagsEnum;
import com.anynote.note.api.model.po.Note;
import com.anynote.note.model.bo.NoteQueryParam;
import com.anynote.note.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 称霸幼儿园
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.note-topic}",
        consumerGroup = "${anynote.data.rocketmq.note-group}")
public class NoteMessageListener implements RocketMQListener<MessageExt> {


    @Autowired
    private NoteService noteService;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println(messageExt.getTags());
//
        if (NoteTagsEnum.GENERATOR_NOTE_INDEX == NoteTagsEnum.valueOf(messageExt.getTags())) {
            log.info("生成笔记索引...");
            log.info(new String(messageExt.getBody()));
            generateNoteIndex(Long.valueOf(new String(messageExt.getBody())));
        }
    }

    private void generateNoteIndex(Long noteId) {
        NoteQueryParam noteQueryParam = NoteQueryParam.builder()
                .id(noteId)
                .build();
        Note note = noteService.selectNoteById(noteQueryParam);
        EsNoteIndex esNoteIndex = EsNoteIndex.builder()
                .id(note.getId())
                .title(note.getTitle())
                .noteTextId(note.getNoteTextId())
                .knowledgeBaseId(note.getKnowledgeBaseId())
                .status(note.getStatus())
                .dataScope(note.getDataScope())
                .permissions(note.getNotePermissions())
                .deleted(note.getDeleted())
                .createBy(note.getCreateBy())
                .createTime(note.getCreateTime())
                .updateBy(note.getUpdateBy())
                .updateTime(note.getUpdateTime())
                .knowledgeBaseName(note.getKnowledgeBaseName())
                .content(note.getContent())
                .submitTaskName(note.getSubmitTaskName())
                .build();
        IndexResponse response = null;
        try {
             response = elasticsearchClient.index(i -> i
                    .index(ElasticsearchIndexConstants.NOTE_INDEX)
                    .id(String.valueOf(esNoteIndex.getId()))
                    .document(esNoteIndex));
        } catch (IOException e) {
            log.error("创建笔记索引失败，id: " + esNoteIndex.getId());
            throw new RuntimeException(e);
        }
        log.info("创建笔记索引成功，id: " + esNoteIndex.getId() + ", version: " + response.version());
    }
}
