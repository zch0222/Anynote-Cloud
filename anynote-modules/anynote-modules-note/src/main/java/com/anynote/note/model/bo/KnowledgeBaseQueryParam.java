package com.anynote.note.model.bo;

import com.anynote.core.web.model.bo.BaseEntity;
import lombok.*;

/**
 * 知识库查询参数类
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeBaseQueryParam extends BaseEntity {

    private Long id;

    private String name;

    private Integer type;

    private Integer status;

    private Long organizationId;

    private Integer page;

    private Integer pageSize;

    private Integer permissions;

    public KnowledgeBaseQueryParam(Long id, Integer page, Integer pageSize) {
        this.id = id;
        this.page = page;
        this.pageSize = pageSize;
    }
}
