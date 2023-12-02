package com.anynote.file.api.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传进度
 * @author 称霸幼儿园
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadProgress {

    /**
     * 已经传输的字节数
     */
    private Long bytesRead;

    /**
     * 传的文件的总大小（以字节为单位）
     */
    private Long contentLength;

    /**
     * 当前第几个item
     */
    private int currentItems;

    private Double progress;

}
