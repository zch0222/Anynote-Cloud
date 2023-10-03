package com.anynote.note.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 知识库创建传输类
 * @author 称霸幼儿园
 */
@Data
public class KnowledgeBaseCreateDTO {

    /**
     * 知识库名称
     */
    @NotBlank(message = "知识库名称不能为空")
    @Size(max = 15, min = 1, message = "知识库名称长度在1-15")
    private String name;

    /**
     * 知识库
     */
    @Size(max = 450, message = "知识库描述长度不能超过450字")
    private String detail;

    @NotNull(message = "知识库封面不能为空")
    private String cover;

    /**
     * 知识库类型
     */
    @NotNull(message = "知识库类型不能为空")
    private Integer type;

    /**
     * 所属组织id
     */
    private Long organizationId;
}
