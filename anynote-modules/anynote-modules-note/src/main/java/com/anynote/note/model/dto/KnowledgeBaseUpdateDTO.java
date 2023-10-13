package com.anynote.note.model.dto;


import lombok.Data;

/**
 *
 * @author 称霸幼儿园
 */
@Data
public class KnowledgeBaseUpdateDTO {
    /**
     * 知识库id
     */
    private Long knowledgeBaseId;

    /**
     * 知识库名称
     */
    private String name;


    /**
     * 知识库封面
     */
    private String cover;

    /**
     * 知识库简介
     */
    private String detail;
}
