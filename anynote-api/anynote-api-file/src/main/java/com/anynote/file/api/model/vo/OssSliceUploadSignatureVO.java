package com.anynote.file.api.model.vo;

import com.anynote.file.api.model.bo.OSSSignature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 对象存储上传签名
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssSliceUploadSignatureVO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignatureInfo {
        private Integer index;

        private OSSSignature signature;
    }

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
     * 签名
     */
    private List<SignatureInfo> signatures;
}
