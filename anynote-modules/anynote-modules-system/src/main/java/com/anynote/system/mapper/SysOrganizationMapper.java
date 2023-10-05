package com.anynote.system.mapper;

import com.anynote.system.api.model.po.SysOrganization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构 Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface SysOrganizationMapper extends BaseMapper<SysOrganization> {

    public SysOrganization selectOrganizationInfoByKnowledgeBaseId(Long id);

    public Integer insertUserSysOrganization(@Param("userId") Long userId,
                                             @Param("sysOrganizationId") Long sysOrganizationId,
                                             @Param("status") Integer status);
}
