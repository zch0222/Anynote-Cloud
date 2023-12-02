package com.anynote.file.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * @author 称霸幼儿园
 */
@TableName("f_file")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePO extends BaseEntity {
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
     * 文件URL地址
     */
    private String url;

    /**
     * 来源 0.笔记图片 1.知识库封面 3.知识库文档
     */
    private Integer source;

    /**
     * 删除标记 0正常 1删除
     */
    @TableField("is_delete")
    @TableLogic
    private Integer deleted;

    /**
     * 文件类型
     */
    private String type;

    @Builder
    public FilePO(Long id, String hash, String originalFileName, String fileName, String url, Integer source,
                  Integer deleted, String type, Long createBy, Date createTime, Long updateBy,
                  Date updateTime, String remark, Map<String, Object> params) {
        super(createBy, createTime, updateBy, updateTime, remark, params);
        this.hash = hash;
        this.id = id;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.url = url;
        this.source = source;
        this.deleted = deleted;
        this.type = type;
    }

}
