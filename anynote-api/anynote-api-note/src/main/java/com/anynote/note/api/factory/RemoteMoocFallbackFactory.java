package com.anynote.note.api.factory;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.api.RemoteMoocService;
import com.anynote.note.api.model.dto.MoocAsrInfoUpdateDTO;
import com.anynote.note.api.model.vo.MoocVideoItemInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Slf4j
@Component
public class RemoteMoocFallbackFactory implements FallbackFactory<RemoteMoocService> {

    @Override
    public RemoteMoocService create(Throwable cause) {
        return new RemoteMoocService() {
            @Override
            public ResData<String> updateAsrInfo(MoocAsrInfoUpdateDTO moocAsrInfoUpdateDTO, String fromSource) {
                throw new BusinessException("调用moocs/asr失败");
            }


            @Override
            public ResData<MoocVideoItemInfoVO> getMoocVideoItemInfo(Long moocItemId, Long moocId,
                                                                     String fromSource, String accessToken) {
                throw new BusinessException("调用moocs/videoItemInfo失败");
            }
        };
    }
}
