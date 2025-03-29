package com.anynote.note.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoocAsrInfoUpdateDTO {

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 对象名称
     */
    private String srtObjectName;
}
