package com.anynote.system.api.model.bo;

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
public class SysUserQueryParam {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 页面大小
     */
    private Integer pageSize;
}
