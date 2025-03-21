package com.anynote.file.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadObjectDTO {

    /**
     * 对象名称
     */
    private String objectName;

    /**
     * 文件保存路径（绝对路径）
     */
    private String fileFolder;
}
