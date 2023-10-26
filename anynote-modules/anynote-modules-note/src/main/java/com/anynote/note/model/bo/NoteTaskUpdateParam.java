package com.anynote.note.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
public class NoteTaskUpdateParam extends NoteTaskQueryParam {

    /**
     * 任务名称
     */
    private String taskName;

    private Date startTime;

    private Date endTime;


    @Builder(builderMethodName = "NoteTaskUpdateParamBuilder")
    public NoteTaskUpdateParam(String taskName,
                               Date endTime, Date startTime, Long noteTaskId) {
        this.taskName = taskName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.setNoteTaskId(noteTaskId);
    }

    public Long getKnowledgeBaseId() {
        return this.getId();
    }



}
