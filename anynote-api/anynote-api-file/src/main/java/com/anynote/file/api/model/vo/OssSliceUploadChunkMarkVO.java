package com.anynote.file.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * 对象存储分片上传完成返回
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OssSliceUploadChunkMarkVO {

    /**
     * 标记的chunkIndex列表
     */
    private List<Integer> markedIndexList;
}
