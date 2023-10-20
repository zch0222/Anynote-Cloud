package com.anynote.note.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 称霸幼儿园
 */
@TableName("n_note")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note extends BaseEntity {

    /**
     * 笔记id
     */
    private Long id;

    /**
     * 笔记标题
     */
    private String title;

    /**
     * 笔记正文id
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long noteTextId;

    /**
     * 所属知识库id
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long knowledgeBaseId;

    /**
     * 状态  0.正常 1.垃圾桶
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer status;

    /**
     * 数据权限 1.自己可见 2.自己和管理员可见 3.知识库中所有人可见
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer dataScope;

    /**
     * 笔记权限
     * 权限(作者 知识库管理员 同知识库用户 其它用户)
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String permissions;

    @TableLogic
    @TableField("is_delete")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer deleted;


    @Deprecated
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private NoteText noteText;

    /**
     * 笔记正文
     */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String content;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer notePermissions;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String knowledgeBaseName;

    @TableField(exist = false)
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String submitTaskName;
}
