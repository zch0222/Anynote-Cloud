package com.anynote.file.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 合并文件分片VO
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssSliceUploadComposeOV {

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 对象名
     */
    private String objectName;

    /**
     * 文件md5
     */
    private String hash;

}
