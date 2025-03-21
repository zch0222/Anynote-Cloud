package com.anynote.ai.api;

import com.anynote.ai.api.factory.RemoteTranslateFallbackFactory;
import com.anynote.ai.api.model.dto.WhisperDTO;
import com.anynote.ai.api.model.vo.WhisperSubmitVO;
import com.anynote.core.constant.ServiceNameConstants;
import com.anynote.core.web.model.bo.ResData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteWhisperService",
        value = ServiceNameConstants.AI_NIO_SERVICE, fallbackFactory = RemoteTranslateFallbackFactory.class)
public interface RemoteWhisperService {

    @PostMapping("whisper/submit")
    public ResData<WhisperSubmitVO> submitWhisperTask(@Validated @RequestBody WhisperDTO whisperDTO,
                                                      @RequestHeader("from-source") String fromSource);
}
