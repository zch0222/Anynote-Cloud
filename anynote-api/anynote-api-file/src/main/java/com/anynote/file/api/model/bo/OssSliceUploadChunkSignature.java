package com.anynote.file.api.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OSS分片上传分片签名
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OssSliceUploadChunkSignature {

    /**
     * 分片编号
     */
    private Integer chunkIndex;

    /**
     * 上传签名
     */
    private OSSSignature signature;
}
