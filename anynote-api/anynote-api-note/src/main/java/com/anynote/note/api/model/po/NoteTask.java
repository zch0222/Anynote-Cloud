package com.anynote.note.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@TableName("n_note_task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteTask extends BaseEntity {
    /**
     * 笔记任务id
     */
    private Long id;

    /**
     * 任务名称
     */
    private String taskName;

    private Date startTime;

    private Date endTime;

    private Long knowledgeBaseId;

    private Integer status;

    private String taskDescribe;

    /**
     * 已经提交的数量
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long submittedCount;

    @TableLogic
    @TableField("is_delete")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer deleted;
}
