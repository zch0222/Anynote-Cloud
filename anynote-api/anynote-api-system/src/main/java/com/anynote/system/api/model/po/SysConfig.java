package com.anynote.system.api.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysConfig {

    /**
     * 配置id
     */
    private Long id;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置值
     */
    private String value;

    /**
     * 描述
     */
    private String description;
}
