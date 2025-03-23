package com.anynote.note.model.dto;

import lombok.Data;

@Data
public class MoocUpdateDTO {

    /**
     * 慕课标题
     */
    private String title;

    /**
     * 封面
     */
    private String cover;

    /**
     * 慕课描述
     */
    private String moocDescription;

    /**
     * 数据权限 1.自己可见 2.自己和管理员可见 3.知识库中所有人可见
     */
    private Integer dataScope;

    /**
     * 所属知识库id 0表示不属于任何知识库
     */
    private Long knowledgeBaseId;

    /**
     * 权限(作者 知识库管理员 同知识库用户 其它用户 匿名用户)
     */
    private String permissions;

}
