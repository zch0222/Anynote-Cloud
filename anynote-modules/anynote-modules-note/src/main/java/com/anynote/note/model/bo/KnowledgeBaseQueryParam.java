package com.anynote.note.model.bo;

import com.anynote.core.web.model.bo.BaseEntity;
import lombok.Data;

/**
 * 知识库查询参数类
 * @author 称霸幼儿园
 */
@Data
public class KnowledgeBaseQueryParam extends BaseEntity {

    private Long id;

    private String name;

    private Integer type;

    private Integer status;

    private Long organizationId;
}
