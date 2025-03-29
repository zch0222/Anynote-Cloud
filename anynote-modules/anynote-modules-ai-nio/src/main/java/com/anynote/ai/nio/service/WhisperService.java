package com.anynote.ai.nio.service;

import com.anynote.ai.api.model.vo.WhisperTaskStatusVO;
import com.anynote.ai.nio.model.bo.WhisperTaskQueryParam;
import com.anynote.ai.api.model.dto.WhisperDTO;
import com.anynote.ai.api.model.vo.WhisperSubmitVO;
import com.anynote.ai.nio.model.vo.WhisperTaskStatusVOV1;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WhisperService {


    public Flux<ServerSentEvent<String>> whisper(WhisperDTO whisperDTO);

    public Mono<WhisperSubmitVO> submitWhisper(WhisperDTO whisperDTO);

    public Flux<ServerSentEvent<WhisperTaskStatusVO>> whisperTaskStatus(WhisperTaskQueryParam queryParam);

    public Flux<WhisperTaskStatusVOV1> whisperTaskStatusV1(WhisperTaskQueryParam queryParam);

    public void whisperV1(WhisperDTO whisperDTO, Long taskId);


}
