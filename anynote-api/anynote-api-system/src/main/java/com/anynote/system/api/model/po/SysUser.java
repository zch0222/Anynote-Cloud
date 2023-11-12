package com.anynote.system.api.model.po;

import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.web.model.bo.BaseEntity;
import com.anynote.system.api.model.bo.LoginUser;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户对象
 *
 * @author 称霸幼儿园
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 性别 0男 1女 2未知
     */
    private Integer sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String password;

    /**
     * 账号状态 0正常 1停用
     */
    private Integer status;

    /**
     * 删除标记 1表示删除 0表示未删除
     */
    @TableField("is_delete")
    private Integer deleted;


    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 角色对象
     */
    @TableField(exist = false)
    private SysRole role;

    @TableField(exist = false)
    private List<SysOrganization> organizations;

    public static boolean isAdminX(SysRole role) {
        return role.getRoleKey().equals(SecurityConstants.ADMIN_X);
    }
}
