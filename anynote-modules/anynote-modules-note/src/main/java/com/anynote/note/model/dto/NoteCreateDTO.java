package com.anynote.note.model.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 笔记创建DTO
 * @author 称霸幼儿园
 */
@Data
public class NoteCreateDTO {

    @NotBlank(message = "笔记标题不能为空")
    @Size(max = 15, min = 3)
    private String title;

    /**
     * 知识库id
     */
    @NotNull(message = "知识库id不能为空")
    private Long knowledgeBaseId;
}
