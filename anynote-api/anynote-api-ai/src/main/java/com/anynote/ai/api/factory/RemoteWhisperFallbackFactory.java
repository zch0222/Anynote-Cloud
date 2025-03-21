package com.anynote.ai.api.factory;

import com.anynote.ai.api.RemoteTranslateService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteWhisperFallbackFactory implements FallbackFactory<RemoteWhisperFallbackFactory> {
    @Override
    public RemoteWhisperFallbackFactory create(Throwable cause) {
        return null;
    }
}
