package com.anynote.note.model.bo;

import com.anynote.note.model.dto.NoteTaskCreateDTO;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 笔记任务创建参数
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteTaskCreateParam extends KnowledgeBaseQueryParam {
    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    private String taskName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    private String taskDescribe;

    public NoteTaskCreateParam(NoteTaskCreateDTO noteTaskCreateDTO) {
        this.setId(noteTaskCreateDTO.getKnowledgeBaseId());
        this.taskName = noteTaskCreateDTO.getTaskName();
        this.startTime = noteTaskCreateDTO.getStartTime();
        this.endTime = noteTaskCreateDTO.getEndTime();
        this.taskDescribe = noteTaskCreateDTO.getTaskDescribe();
    }
}
