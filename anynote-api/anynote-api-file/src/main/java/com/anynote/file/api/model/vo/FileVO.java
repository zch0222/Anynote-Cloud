package com.anynote.file.api.model.vo;

import com.anynote.file.api.model.bo.ObjectURL;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileVO implements Serializable {

    private static final long serialVersionUID = 2975271656230801861L;

    /**
     * 文件id
     */
    private Long id;

    /**
     * 原始文件名
     */
    private String originalFileName;

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
     * 文件URL地址
     */
    private ObjectURL objectURL;

    /**
     * 来源 0.笔记图片 1.知识库封面 3.知识库文档
     */
    private Integer source;

    /**
     * 文件类型
     */
    private String type;

    /** 创建者 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long createBy;

    /** 创建时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date createTime;

    /** 更新者 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long updateBy;

    /** 更新时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
