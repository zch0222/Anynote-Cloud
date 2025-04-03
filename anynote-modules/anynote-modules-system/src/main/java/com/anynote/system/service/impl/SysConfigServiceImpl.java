package com.anynote.system.service.impl;

import com.anynote.common.redis.service.RedisService;
import com.anynote.system.api.model.po.SysConfig;
import com.anynote.system.mapper.SysConfigMapper;
import com.anynote.system.service.SysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统配置实现
 * @author 称霸幼儿园
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig>
        implements SysConfigService {

    @Resource
    private RedisService redisService;

    @Override
    public List<SysConfig> getSysConfigs() {
        List<SysConfig> sysConfigList = this.baseMapper
                .selectList(new LambdaQueryWrapper<SysConfig>()
                        .select(SysConfig::getId, SysConfig::getName));
        return redisService
                .getMulti(sysConfigList.stream()
                        .map(SysConfig::getName)
                        .collect(Collectors.toList()));
    }
}
