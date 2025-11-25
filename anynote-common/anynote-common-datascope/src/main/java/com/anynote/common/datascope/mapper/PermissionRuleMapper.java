package com.anynote.common.datascope.mapper;

import com.anynote.common.datascope.model.bo.EntityPermissionOneToOneQueryParam;
import com.anynote.common.datascope.model.bo.EntityPermissionQueryParam;
import com.anynote.common.datascope.model.bo.UserAssociatedPermissionQueryParam;
import com.anynote.common.datascope.model.po.EntityPermissionPO;
import com.anynote.common.datascope.model.po.UserAssociatedEntityPermissionPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionRuleMapper {

    /**
     * 存在一对一知识库资源关联表的情况下，获取权限
     * @param queryParam
     * @return
     */
    public EntityPermissionPO selectEntityPermissionOneToOne(EntityPermissionQueryParam queryParam);

    /**
     * 存在n对m知识库资源关联表的情况下，获取权限
     * @param queryParam
     * @return
     */
    public EntityPermissionPO selectEntityPermissionNToM(EntityPermissionQueryParam queryParam);

    /**
     * 获取用户关联的资源的权限
     * @param queryParam
     * @return
     */
    public UserAssociatedEntityPermissionPO selectUserAssociatedPermission(UserAssociatedPermissionQueryParam queryParam);
}
