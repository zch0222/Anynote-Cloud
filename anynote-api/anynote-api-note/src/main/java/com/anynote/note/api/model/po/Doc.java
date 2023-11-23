package com.anynote.note.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文档
 * @author 称霸幼儿园
 */
@Data
@Builder
@TableName("n_doc")
@NoArgsConstructor
@AllArgsConstructor
public class Doc extends BaseEntity {

    /**
     * 文档id id
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
     * 文件URL地址
     */
    private String url;

    /**
     * 知识库id
     */
    private Long knowledgeBaseId;

    /**
     * 文档类型 1. PDF
     */
    private Integer type;

    /**
     * 数据权限 1.自己可见 2.自己和管理员可见 3.知识库中所有人可见
     */
    private Integer dataScope;

    /**
     * 权限(作者(创建者) 知识库管理员 同知识库用户 其它用户 匿名用户)
     */
    private String permissions;

    /**
     * 删除标记 0.正常 1.删除
     */
    @TableLogic
    @TableField("is_delete")
    private Integer deleted;
}
