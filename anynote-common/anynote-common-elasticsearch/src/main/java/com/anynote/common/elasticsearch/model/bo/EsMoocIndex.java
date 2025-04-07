package com.anynote.common.elasticsearch.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsMoocIndex {

    private Long id;

    private String title;

    private Long knowledgeBaseId;

    private Integer dataScope;

    private String permissions;

    private Integer deleted;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;
}
