package com.anynote.note.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.alibaba.fastjson2.JSON;
import com.anynote.common.elasticsearch.constant.ElasticsearchIndexConstants;
import com.anynote.common.elasticsearch.model.EsNoteIndex;
import com.anynote.core.exception.BusinessException;
import com.anynote.note.service.NoteElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 笔记搜索服务
 * @author 称霸幼儿园
 */
@Slf4j
@Service
public class NoteElasticsearchServiceImpl implements NoteElasticsearchService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public Integer deleteNoteIndex(Long noteId) {
        DeleteRequest deleteRequest = new DeleteRequest.Builder()
                .index(ElasticsearchIndexConstants.NOTE_INDEX)
                .id(String.valueOf(noteId))
                .build();
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = elasticsearchClient.delete(deleteRequest);
        } catch (IOException e) {
            log.error("删除索引笔记索引" + noteId + "失败");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        log.info("删除索引笔记索引" + noteId + "成功");
        return (Integer) deleteResponse.shards().successful();
    }
}
