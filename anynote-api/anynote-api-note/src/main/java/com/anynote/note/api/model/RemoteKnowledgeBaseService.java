package com.anynote.note.api.model;

import com.anynote.core.constant.ServiceNameConstants;
import com.anynote.note.api.model.factory.RemoteKnowledgeBaseFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 称霸幼儿园
 */
@FeignClient(contextId = "remoteKnowledgeBaseService",
        value = ServiceNameConstants.NOTE_SERVICE,
        fallbackFactory = RemoteKnowledgeBaseFallbackFactory.class)
public interface RemoteKnowledgeBaseService {

    @GetMapping
}
