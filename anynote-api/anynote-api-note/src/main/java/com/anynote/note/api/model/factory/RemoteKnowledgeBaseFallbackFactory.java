package com.anynote.note.api.model.factory;

import com.anynote.note.api.model.RemoteKnowledgeBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author 称霸幼儿园
 */
@Slf4j
@Component
public class RemoteKnowledgeBaseFallbackFactory implements FallbackFactory<RemoteKnowledgeBaseService> {


    @Override
    public RemoteKnowledgeBaseService create(Throwable cause) {
        log.error("知识库服务调用失败: {}", cause.getMessage());
        return new RemoteKnowledgeBaseService() {
        };
    }
}
