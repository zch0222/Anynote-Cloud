package com.anynote.system.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 组织表
 * @author 称霸幼儿园
 */
@Data
@TableName("sys_organization")
public class SysOrganization extends BaseEntity {
    /**
     * 组织id
     */
    private Long id;

    /**
     * 父组织id
     */
    private Long parentId;

    /**
     * 祖先列表
     */
    private String ancestors;

    /**
     * 组织名称
     */
    private String organizationName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门状态 (0正常 1停用)
     */
    private Integer status;

    /**
     * 删除标志(0标识未删除 1表示删除)
     */
    @TableLogic
    @TableField("is_delete")
    private Integer deleted;
}
