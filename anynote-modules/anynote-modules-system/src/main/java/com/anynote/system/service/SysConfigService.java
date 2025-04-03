package com.anynote.system.service;

import com.anynote.system.api.model.po.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统配置服务类
 * @author 称霸幼儿园
 */
public interface SysConfigService extends IService<SysConfig> {


    public List<SysConfig> getSysConfigs();
}
