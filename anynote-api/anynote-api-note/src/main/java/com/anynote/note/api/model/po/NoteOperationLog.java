package com.anynote.note.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 笔记操作记录表
 * @author 称霸幼儿园
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("n_note_operation_log")
public class NoteOperationLog extends BaseEntity {
    /**
     * 操作id
     */
    private long id;

    /**
     * 笔记id
     */
    private long noteId;

    /**
     * 操作类型 1.编辑 2.管理 3.评价
     */
    private int operationType;

    /**
     * 操作者id
     */
    private long operatorId;

    /**
     * 操作时间
     */
    private Date operationTime;

    @TableLogic
    @TableField("is_delete")
    private Integer deleted;
}
