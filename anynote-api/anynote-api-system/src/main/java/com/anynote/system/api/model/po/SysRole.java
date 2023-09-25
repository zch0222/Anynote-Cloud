package com.anynote.system.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 系统角色类
 *
 * @author 称霸幼儿园
 */
@Data
@TableName("sys_role")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String roleName;

    private String roleKey;

    private Long roleSort;

    private String dataScope;

    private String status;

    @TableField("is_delete")
    @TableLogic
    private Integer deleted;



}
