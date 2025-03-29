package com.anynote.note.api;

import com.anynote.core.constant.ServiceNameConstants;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.api.factory.RemoteKnowledgeBaseFallbackFactory;
import com.anynote.note.api.model.dto.MoocAsrInfoUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteMoocService",
        value = ServiceNameConstants.NOTE_SERVICE,
        fallbackFactory = RemoteKnowledgeBaseFallbackFactory.class)
public interface RemoteMoocService {

    @PutMapping("moocs/asr")
    public ResData<String> updateAsrInfo(@RequestBody MoocAsrInfoUpdateDTO moocAsrInfoUpdateDTO,
                                         @RequestHeader("from-source") String fromSource);
}
