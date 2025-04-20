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
            // 构建 ffmpeg 命令
            Process process = new ProcessBuilder(
                    "ffmpeg",
                    "-i", filePath,       // 输入文件
                    "-vn",               // 禁用视频流
                    "-acodec", "libmp3lame", // 使用 MP3 编码
                    "-ab", "64k",        // 更低的音频比特率（原为192k）
                    "-ar", "22050",      // 降低采样率（CD质量是44100）
                    "-ac", "1",          // 单声道（原默认是立体声2）
                    "-y",                // 覆盖输出文件（如果存在）
                    audioPath.toString()
            ).start();
//            Process process = new ProcessBuilder(
//                    "ffmpeg", "-i", filePath, "-vn", "-c:a", "copy", audioPath.toString())
//                    .start();
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
