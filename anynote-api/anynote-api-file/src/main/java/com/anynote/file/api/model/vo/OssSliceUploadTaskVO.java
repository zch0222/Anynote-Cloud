package com.anynote.file.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssSliceUploadTaskVO {

    private String uploadId;

    private String originalFileName;

    private String fileName;

    private Double fileSize;

    /**
     * 分片大小
     */
    private Integer chunkSize;

    /**
     * 分片数量
     */
    private Integer totalChunk;

    private String hash;

    /**
     * 已经完成的分片编号
     */
    private Set<Integer> finishedChunks;
}
