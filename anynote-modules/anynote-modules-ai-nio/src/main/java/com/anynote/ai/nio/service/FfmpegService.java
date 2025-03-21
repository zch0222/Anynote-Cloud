package com.anynote.ai.nio.service;

public interface FfmpegService {


    /**
     * 复制文件的音频
     * @param filePath 文件路径
     * @param audioSaveFolder 音频保存文件夹
     * @return 复制出的音频文件路径
     */
    public String copyAudio(String filePath, String audioSaveFolder);

    public String copyAudio(String filePath);

}
