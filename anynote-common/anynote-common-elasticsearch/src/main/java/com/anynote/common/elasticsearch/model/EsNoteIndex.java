package com.anynote.common.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsNoteIndex {
    private Long id;

    private String title;

    private Long noteTextId;

    private Long knowledgeBaseId;

    private Integer status;

    private Integer dataScope;

    private Integer permissions;

    private Integer deleted;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private String knowledgeBaseName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String content;

    private String submitTaskName;
}
