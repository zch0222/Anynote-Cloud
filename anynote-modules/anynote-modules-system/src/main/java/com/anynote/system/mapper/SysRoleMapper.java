package com.anynote.system.mapper;

import com.anynote.system.api.model.po.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 角色Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id选取该用户的角色的权限
     * @param userId 用户id
     * @return 权限列表
     */
    public Set<String> selectRolePermissionByUserId(@Param("userId") Long userId);
}
