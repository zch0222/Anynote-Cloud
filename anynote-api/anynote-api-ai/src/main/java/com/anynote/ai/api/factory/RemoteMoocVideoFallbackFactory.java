package com.anynote.ai.api.factory;

import com.anynote.ai.api.RemoteMoocVideoSummarizeService;
import com.anynote.ai.api.model.dto.GetMoocVideoSummarizesByMoocIdDTO;
import com.anynote.ai.api.model.po.MoocVideoSummarizePO;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.web.model.bo.ResData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class RemoteMoocVideoFallbackFactory implements FallbackFactory<RemoteMoocVideoSummarizeService> {


    @Override
    public RemoteMoocVideoSummarizeService create(Throwable cause) {
        return null;
    }
}
