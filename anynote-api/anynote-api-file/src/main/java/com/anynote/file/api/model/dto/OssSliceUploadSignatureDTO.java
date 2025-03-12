package com.anynote.file.api.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author 称霸幼儿园
 */
@Data
public class OssSliceUploadSignatureDTO {

    /**
     * 上传id
     */
    @NotNull(message = "上传id不能为空")
    private String uploadId;

    /**
     * 需要的分片index
     */
    @NotNull(message = "分片index不能为空")
    @NotEmpty(message = "分片index不能为空")
    private Set<Integer> chunkIndexList;
}

