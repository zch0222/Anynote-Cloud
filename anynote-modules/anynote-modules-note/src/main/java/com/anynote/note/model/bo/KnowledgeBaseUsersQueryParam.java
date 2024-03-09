package com.anynote.note.model.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 知识库用户查询类
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KnowledgeBaseUsersQueryParam extends KnowledgeBaseQueryParam {
    private String username;
}
