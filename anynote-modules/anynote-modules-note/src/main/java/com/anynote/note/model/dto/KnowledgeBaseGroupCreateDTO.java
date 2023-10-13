package com.anynote.note.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeBaseGroupCreateDTO {

    @NotBlank(message = "分组名称不能为空")
    @Size(max = 20, min = 1, message = "分组名称长度必须在1-20")
    private String groupName;

    @NotNull(message = "知识库id不能为空")
    private Long knowledgeBaseId;
}
