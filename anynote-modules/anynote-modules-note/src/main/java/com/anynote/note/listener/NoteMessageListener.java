package com.anynote.note.listener;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.alibaba.fastjson2.JSON;
import com.anynote.common.elasticsearch.constant.ElasticsearchIndexConstants;
import com.anynote.common.elasticsearch.model.EsNoteIndex;
import com.anynote.common.rocketmq.tags.NoteTagsEnum;
import com.anynote.note.api.model.bo.GenerateNoteEditLogMessage;
import com.anynote.note.api.model.po.Note;
import com.anynote.note.api.model.po.NoteEditLog;
import com.anynote.note.api.model.po.NoteOperationLog;
import com.anynote.note.enums.NoteOperationType;
import com.anynote.note.mapper.NoteMapper;
import com.anynote.note.model.bo.NoteQueryParam;
import com.anynote.note.service.*;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.note-topic}",
        consumerGroup = "${anynote.data.rocketmq.note-group}")
public class NoteMessageListener implements RocketMQListener<MessageExt> {

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private NoteElasticsearchService noteElasticsearchService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteOperationLogService noteOperationLogService;

    @Autowired
    private NoteHistoryService noteHistoryService;

    @Autowired
    private NoteEditLogService noteEditLogService;

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
        else if (NoteTagsEnum.GENERATE_NOTE_EDIT_LOG == NoteTagsEnum.valueOf(messageExt.getTags())) {
            log.info(new String(messageExt.getBody()));
            GenerateNoteEditLogMessage generateNoteEditLogMessage = JSON.parseObject(new String(messageExt.getBody()), GenerateNoteEditLogMessage.class);
            generateNoteEditLog(generateNoteEditLogMessage);
        }
        else if (NoteTagsEnum.DELETE_NOTE_INDEX == NoteTagsEnum.valueOf(messageExt.getTags())) {
            log.info("删除索引...");
            noteElasticsearchService.deleteNoteIndex(Long.valueOf(new String(messageExt.getBody())));

        }
    }

    private void generateNoteEditLog(GenerateNoteEditLogMessage generateNoteEditLogMessage) {
        List<String> oldContent = Arrays.asList(generateNoteEditLogMessage.getOldNote().getContent().split("\\r?\\n"));
        List<String> newContent = Arrays.asList(generateNoteEditLogMessage.getCurrentNote().getContent().split("\\r?\\n"));
        log.info("old---" + JSON.toJSONString(oldContent));
        log.info("new---" + JSON.toJSONString(newContent));
        Patch<String> patch = DiffUtils.diff(oldContent, newContent);
        log.info(JSON.toJSONString(patch));
        if (!patch.getDeltas().isEmpty()) {
            NoteOperationLog noteOperationLog = NoteOperationLog.builder()
                    .noteId(generateNoteEditLogMessage.getNoteId())
                    .operatorId(generateNoteEditLogMessage.getUserId())
                    .operationType(NoteOperationType.EDIT.getValue())
                    .operationTime(generateNoteEditLogMessage.getDate())
                    .build();
            Date date = new Date();
            noteOperationLog.setCreateBy(generateNoteEditLogMessage.getUserId());
            noteOperationLog.setCreateTime(date);
            noteOperationLog.setUpdateBy(generateNoteEditLogMessage.getUserId());
            noteOperationLog.setUpdateTime(date);
            log.debug(JSON.toJSONString(noteOperationLog));
            noteOperationLogService.getBaseMapper().insert(noteOperationLog);
            this.saveNoteEditLogs(patch, noteOperationLog.getId(), generateNoteEditLogMessage);
            noteHistoryService.saveNoteHistory(generateNoteEditLogMessage.getCurrentNote(),
                    noteOperationLog.getId(), generateNoteEditLogMessage.getDate(), generateNoteEditLogMessage.getUserId());
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

    private void saveNoteEditLogs(Patch<String> patch, Long operationId,
                                  GenerateNoteEditLogMessage generateNoteEditLogMessage) {

        for (Delta<String> delta : patch.getDeltas()) {
            log.info("Original:---" + (delta.getOriginal().getLines().isEmpty() ? "" : delta.getOriginal().getLines().get(0)));
            log.info("Revised:---" + (delta.getRevised().getLines().isEmpty() ? "" : delta.getRevised().getLines().get(0)));
            int j = delta.getRevised().getLines().size() > delta.getOriginal().getLines().size() ? delta.getRevised().getLines().size() : delta.getOriginal().getLines().size();
            for (int i = 0; i < j; ++i) {
                NoteEditLog noteEditLog = NoteEditLog.builder()
                        .operationId(operationId)
                        .originalText(delta.getOriginal().getLines().isEmpty() || delta.getOriginal().getLines().size() < i + 1 ? "" : delta.getOriginal().getLines().get(i))
                        .revisedText(delta.getRevised().getLines().isEmpty() || delta.getRevised().getLines().size() < i + 1 ? "" :  delta.getRevised().getLines().get(i))
                        .originalPosition(delta.getOriginal().getPosition())
                        .revisedPosition(delta.getRevised().getPosition() + i)
                        .changeType(delta.getType().ordinal())
                        .build();
                Date date = new Date();
                noteEditLog.setCreateBy(generateNoteEditLogMessage.getUserId());
                noteEditLog.setCreateTime(date);
                noteEditLog.setUpdateBy(generateNoteEditLogMessage.getUserId());
                noteEditLog.setUpdateTime(date);
                noteEditLogService.getBaseMapper().insert(noteEditLog);
            }
        }
    }


}
