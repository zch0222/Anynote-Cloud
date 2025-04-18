package com.anynote.ai.api;

import com.anynote.ai.api.model.dto.GetMoocVideoSummarizesByMoocIdDTO;
import com.anynote.ai.api.model.po.MoocVideoSummarizePO;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.constant.ServiceNameConstants;
import com.anynote.core.web.model.bo.ResData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "remoteMoocVideoSummarizeService",
        value = ServiceNameConstants.AI_NIO_SERVICE)
public interface RemoteMoocVideoSummarizeService {

    @PostMapping("/moocVideoSummarizes/getMoocVideoSummarizesByMoocItemId")
    public ResData<List<MoocVideoSummarizePO>> getMoocVideoSummarizesByMoocItemId(@RequestHeader(SecurityConstants.FROM_SOURCE) String formSource,
                                                                                  @RequestBody GetMoocVideoSummarizesByMoocIdDTO getMoocVideoSummarizesByMoocIdDTO);
}
