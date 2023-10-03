package com.anynote.note.model.dto;

import com.anynote.note.api.model.po.NoteTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员笔记列表
 * @author 称霸幼儿园
 */
@Data
@AllArgsConstructor
public class AdminNoteTaskDTO extends NoteTask {

    /**
     * 需要提交的总人数
     */
    private Long needSubmitCount;

    /**
     * 提交进度
     */
    private Double submissionProgress;

    public AdminNoteTaskDTO(NoteTask noteTask) {
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
        this.setDeleted(null);
    }
}
