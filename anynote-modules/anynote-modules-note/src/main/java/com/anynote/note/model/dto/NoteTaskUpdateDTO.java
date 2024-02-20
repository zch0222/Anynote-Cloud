package com.anynote.note.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteTaskUpdateDTO {

    /**
     * 笔记任务id
     */
    private Long id;

    /**
     * 任务名称
     */
    @Size(max = 20, min = 1, message = "任务名称长度必须在1-20个字符")
    private String taskName;

    private Date startTime;

    private Date endTime;

    private String taskDescribe;
}
