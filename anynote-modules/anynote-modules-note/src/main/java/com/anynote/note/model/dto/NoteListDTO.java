package com.anynote.note.model.dto;

import com.anynote.core.web.model.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 笔记列表获取参数
 * @author 称霸幼儿园
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoteListDTO extends PageDTO {

    private Long knowledgeBaseId;

    // updateTime, createTime
    private String orderBy;
}
