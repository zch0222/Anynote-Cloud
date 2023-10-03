package com.anynote.note.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Data
public class NoteTaskCreateDTO {

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    private String taskName;

    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    /**
     * 知识库id
     */
    @NotNull(message = "知识库id不能为空")
    private Long knowledgeBaseId;
}
