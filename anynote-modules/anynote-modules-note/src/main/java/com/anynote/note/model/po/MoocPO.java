package com.anynote.note.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;
import java.util.Map;

/**
 * 慕课实体类
 *
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@TableName("n_mooc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoocPO extends BaseEntity {
    /**
     * 慕课id
     */
    private Long id;

    /**
     * 慕课标题
     */
    private String title;

    /**
     * 封面
     */
    private String cover;

    /**
     * 慕课描述
     */
    private String moocDescription;

    /**
     * 数据权限 1.自己可见 2.自己和管理员可见 3.知识库中所有人可见
     */
    private Integer dataScope;

    /**
     * 所属知识库id 0表示不属于任何知识库
     */
    private Long knowledgeBaseId;

    /**
     * 权限(作者 知识库管理员 同知识库用户 其它用户 匿名用户)
     */
    private String permissions;

    /**
     * 删除标志(0标识未删除 1表示删除)
     */
    @TableField("is_delete")
    @TableLogic
    private Integer deleted;

    @Builder
    public MoocPO(Long id, String title, String cover, String moocDescription, Integer dataScope, Long knowledgeBaseId,
                  String permissions, Integer deleted, Long createBy, Date createTime, Long updateBy,
                  Date updateTime, String remark, Map<String, Object> params) {
        super(createBy, createTime, updateBy, updateTime, remark, params);
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.moocDescription = moocDescription;
        this.dataScope = dataScope;
        this.knowledgeBaseId = knowledgeBaseId;
        this.permissions = permissions;
        this.deleted = deleted;
    }
}