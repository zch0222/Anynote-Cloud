package com.anynote.note.model.bo;

import com.anynote.core.web.model.bo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteQueryParam extends BaseEntity {

    /**
     * 笔记id
     */
    private Long id;

    /**
     * 笔记标题
     */
    private String title;

    /**
     * 状态标记
     */
    private Integer status;

    /**
     * 组织id
     */
    private Long knowledgeBaseId;

    private Integer page;

    private Integer pageSize;
}
