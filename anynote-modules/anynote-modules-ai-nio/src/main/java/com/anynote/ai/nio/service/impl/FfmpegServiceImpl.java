package com.anynote.ai.nio.service.impl;

import com.anynote.ai.nio.service.FfmpegService;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FfmpegServiceImpl implements FfmpegService {

    @Override
    public String copyAudio(String filePath, String audioSaveFolder) {
        String audioName = StringUtils.format("{}.wav",
                UUID.randomUUID().toString().replace("-", ""));
        Path audioPath = Paths.get(audioSaveFolder).resolve(audioName);
        try {
            Process process = new ProcessBuilder(
                    "ffmpeg", "-i", filePath, "-vn", "-c:a", "copy", audioPath.toString())
                    .start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(StringUtils.format("复制文件\"{}\"音频失败", filePath));
        }
        return audioPath.toString();
    }

    @Override
    public String copyAudio(String filePath) {
        return copyAudio(filePath, Paths.get(filePath).getParent().toString());
    }
}
