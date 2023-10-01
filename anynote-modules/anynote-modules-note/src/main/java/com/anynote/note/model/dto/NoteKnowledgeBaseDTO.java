package com.anynote.note.model.dto;

import com.anynote.core.web.model.bo.BaseEntity;
import com.anynote.note.api.model.po.NoteKnowledgeBase;
import lombok.Data;

/**
 * @author 称霸幼儿园
 */
@Data
public class NoteKnowledgeBaseDTO extends BaseEntity {
    /**
     * 知识库id
     */
    private Long id;

    /**
     * 知识库名称
     */
    private String knowledgeBaseName;

    /**
     * 知识库封面
     */
    private String cover;

    /**
     * 类型 (0.普通知识库 1.课程知识库)
     */
    private Integer type;

    /**
     * 知识库状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 机构id
     */
    private Long organizationId;

    /**
     * 机构名称
     */
    private String organizationName;

    /**
     * 权限
     */
    private Integer permissions;


}
