package com.anynote.note.api.model.vo;

import com.anynote.note.api.model.po.NoteTask;
import lombok.*;

/**
 * 管理员笔记列表
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminNoteTaskVO extends NoteTask {

    /**
     * 需要提交的总人数
     */
    private Long needSubmitCount;

    /**
     * 提交进度
     */
    private Double submissionProgress;

    public AdminNoteTaskVO(NoteTask noteTask) {
        this.setId(noteTask.getId());
        this.setTaskName(noteTask.getTaskName());
        this.setStartTime(noteTask.getStartTime());
        this.setEndTime(noteTask.getEndTime());
        this.setKnowledgeBaseId(noteTask.getKnowledgeBaseId());
        this.setStatus(noteTask.getStatus());
        this.setSubmittedCount(noteTask.getSubmittedCount());
        this.setCreateBy(noteTask.getCreateBy());
        this.setCreateTime(noteTask.getCreateTime());
        this.setUpdateBy(noteTask.getUpdateBy());
        this.setUpdateTime(noteTask.getUpdateTime());
        this.setTaskDescribe(noteTask.getTaskDescribe());
        this.setDeleted(null);
    }
}
