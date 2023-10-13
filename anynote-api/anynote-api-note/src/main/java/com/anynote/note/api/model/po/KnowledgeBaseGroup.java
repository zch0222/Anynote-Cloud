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
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("n_knowledge_base_group")
public class KnowledgeBaseGroup extends BaseEntity {

    /**
     * 分组id
     */
    private Long id;

    /**
     * 父分组id
     */
    private Long parentId;

    /**
     * 祖先分组列表
     */
    private String ancestors;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 显示顺序
     */
    private Long orderNum;

    /**
     * 显示状态 0正常
     */
    private Integer status;

    /**
     * 知识库id
     */
    private Long knowledgeBaseId;

    @TableLogic
    @TableField("is_delete")
    private Integer deleted;
}
