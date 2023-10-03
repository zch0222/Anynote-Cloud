package com.anynote.system.service.impl;

import com.anynote.system.api.model.po.SysConfig;
import com.anynote.system.mapper.SysConfigMapper;
import com.anynote.system.service.SysConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统配置实现
 * @author 称霸幼儿园
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig>
        implements SysConfigService {
}
