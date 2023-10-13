package com.anynote.note.api.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户笔记任务关联表
 * @author 称霸幼儿园
 */
@TableName("n_user_note_task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNoteTask {

    private Long userId;

    private Long noteTaskId;

    /**
     * 任务权限 1.管理 2.提交 3.无权限
     */
    private Integer permissions;
}
