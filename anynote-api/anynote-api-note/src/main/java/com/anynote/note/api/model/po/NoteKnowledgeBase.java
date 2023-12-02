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
 * 知识库表
 * @author 称霸幼儿园
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("n_knowledge_base")
public class NoteKnowledgeBase extends BaseEntity {

    /**
     * 知识库id
     */
    private Long id;

    /**
     * 知识库名称
     */
    private String name;

    /**
     * 知识库封面
     */
    private String cover;

    /**
     * 封面文件id
     */
    private Long coverFileId;

    /**
     * 类型 (0.普通知识库 1.课程知识库)
     */
    private Integer type;

    /**
     * 知识库描述
     */
    private String detail;

    /**
     * 知识库状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 删除标记(0正常 1删除)
     */
    @TableLogic
    @TableField("is_delete")
    private int deleted;
}
