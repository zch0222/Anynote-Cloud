package com.anynote.file.api.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件传输类
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    private Long fileId;

    private String originalFileName;

    private String fileName;

    /**
     * 文件哈希
     */
    private String hash;

    private String url;

    /**
     * 来源 0.笔记图片 1.知识库封面 3.知识库文档
     */
    private Integer source;

}
