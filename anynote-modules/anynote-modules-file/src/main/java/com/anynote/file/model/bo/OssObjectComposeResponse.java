package com.anynote.file.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对象存储文件合并相应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssObjectComposeResponse {

    /**
     * 合并后文件的md5哈希
     */
    private String hash;

    /**
     * 合并后的对象名称
     */
    private String objectName;
}
