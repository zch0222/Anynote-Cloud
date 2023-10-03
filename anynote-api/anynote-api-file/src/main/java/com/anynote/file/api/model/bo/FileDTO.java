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

    private String url;

    /**
     * 文件类型 1.笔记图片
     */
    private Integer type;

}
