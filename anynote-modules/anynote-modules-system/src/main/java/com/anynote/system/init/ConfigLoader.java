package com.anynote.system.init;

import com.anynote.common.redis.service.RedisService;
import com.anynote.core.enums.ConfigEnum;
import com.anynote.system.api.model.po.SysConfig;
import com.anynote.system.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 加载配置信息
 * @author 称霸幼儿园
 */
@Component
public class ConfigLoader implements ApplicationRunner {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysConfigService configService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SysConfig> configList = configService.getBaseMapper().selectList(null);
        for (SysConfig config : configList) {
            redisService.setCacheObject(ConfigEnum.valueOf(config.getName()).name(), config);
        }
    }
}
