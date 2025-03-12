package com.anynote.note.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 慕课VO
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoocVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 慕课id
     */
    private Long id;

    /**
     * 慕课标题
     */
    private String title;

    /**
     * 慕课封面
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
     * 慕课所属知识库名称
     */
    private String moocKnowledgeBaseName;

    /**
     * 慕课权限
     */
    private Integer moocPermission;

    /**
     * 当前用户权限
     */
    private Integer userPermissions;

    /**
     * 慕课创建者用户名
     */
    private String creatorUsername;

    /** 创建者 */
    private Long createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 备注 */
    private String remark;
}
