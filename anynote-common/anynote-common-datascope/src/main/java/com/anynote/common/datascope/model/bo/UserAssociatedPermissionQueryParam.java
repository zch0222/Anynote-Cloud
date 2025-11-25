package com.anynote.common.datascope.model.bo;

import lombok.*;

/**
 * 用户关联权限
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserAssociatedPermissionQueryParam {

    /**
     * 资源id
     */
    private Long entityId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户资源关联表名称
     */
    private String userAssociatedTableName;
}
