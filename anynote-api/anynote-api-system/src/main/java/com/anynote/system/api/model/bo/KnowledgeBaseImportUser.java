package com.anynote.system.api.model.bo;

import com.anynote.core.web.model.bo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeBaseImportUser extends BaseEntity {

    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 班级
     */
    private String className;

    private String password;
}
