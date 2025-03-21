package com.anynote.ai.nio.model.bo;

import lombok.Data;

@Data
public class WhisperConfig {

    private String baseUrl;

    private String apiKey;

    private String tmpFileFolder;
}
