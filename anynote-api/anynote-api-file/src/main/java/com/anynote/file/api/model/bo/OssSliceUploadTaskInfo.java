package com.anynote.file.api.model.bo;

import com.anynote.file.api.model.po.FilePO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssSliceUploadTaskInfo implements Serializable {

    private static final long serialVersionUID = 2975271656230801861L;

    /**
     * 文件信息
     */
    private FilePO fileInfo;

    /**
     * ossType类型
     */
    private String ossType;

    private String chunkFolder;

    /**
     * 合并后的对象名称
     */
    private String objectName;

    /**
     * 分片大小
     */
    private Integer chunkSize;

    /**
     * 分片数量
     */
    private Integer totalChunk;

    private String uploadId;

}
