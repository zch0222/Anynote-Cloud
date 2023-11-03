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
import java.util.Map;

/**
 * @author 称霸幼儿园
 */
@NoArgsConstructor
@AllArgsConstructor
@TableName("n_note_task_operation_history")
@Data
public class NoteTaskOperationHistory extends BaseEntity {

    /**
     * id
     */
    private Long id;

    /**
     * 笔记任务id
     */
    private Long noteTaskId;

    /**
     * 操作类型 1.创建 2.修改任务 3.提交任务 4.退回提交 5.添加用户
     */
    private Integer type;

    /**
     * 操作者id
     */
    private Long operatorId;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 受影响的任务管理用户
     */
    private Long noteTaskUserId;

    /**
     * 笔记提交记录id
     */
    private Long noteTaskSubmissionRecordId;

    /**
     * 0.未删除 1.删除
     */
    @TableLogic
    @TableField("is_delete")
    private Integer deleted;

    @Builder
    public NoteTaskOperationHistory(Long createBy, Date createTime, Long updateBy, Date updateTime, String remark, Map<String, Object> params, Long id, Long noteTaskId, Integer type,
                                    Long operatorId, Date operationTime, Long noteTaskSubmissionRecordId, Integer deleted, Long noteTaskUserId) {
        super(createBy, createTime, updateBy, updateTime, remark, params);
        this.noteTaskUserId = noteTaskUserId;
        this.id = id;
        this.noteTaskId = noteTaskId;
        this.type = type;
        this.operatorId = operatorId;
        this.operationTime = operationTime;
        this.noteTaskSubmissionRecordId = noteTaskSubmissionRecordId;
        this.deleted = deleted;
    }


}
