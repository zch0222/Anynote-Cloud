package com.anynote.file.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssSliceUploadTaskCreateDTO {

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件哈希
     */
    private String hash;

    /**
     * 文件大小
     */
    private Double fileSize;

    /**
     * contentType
     */
    private String contentType;

    /**
     * 文件来源
     */
    private Integer source;

    public OssSliceUploadTaskCreateDTO(OssSliceUploadTaskCreatePublicDTO publicDTO,
                                       String path, Integer source) {
        this.path = path;
        this.source = source;
        this.fileName = publicDTO.getFileName();
        this.hash = publicDTO.getHash();
        this.fileSize = publicDTO.getFileSize();
        this.contentType = publicDTO.getContentType();
    }


}
