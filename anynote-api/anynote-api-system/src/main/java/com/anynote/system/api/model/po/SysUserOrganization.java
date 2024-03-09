package com.anynote.system.api.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 称霸幼儿园
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_organization")
public class SysUserOrganization {

    private Long userId;

    private Long organizationId;

    /**
     * 状态
     * 0.正常 1.退出
     */
    private Long status;
}
