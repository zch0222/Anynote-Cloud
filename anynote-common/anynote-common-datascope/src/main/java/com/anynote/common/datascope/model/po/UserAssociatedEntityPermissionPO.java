package com.anynote.common.datascope.model.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户关联的资源的权限PO
 */
@Data
@EqualsAndHashCode
public class UserAssociatedEntityPermissionPO {

    /**
     * 实体id
     */
    private Long entityId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 权限
     */
    private Integer permission;

    /**
     * 删除标记 0未删除，1表示删除
     */
    private Integer deleted;
}
