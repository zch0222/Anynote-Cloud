package com.anynote.common.redis.service;

import com.anynote.core.enums.ConfigEnum;
import com.anynote.system.api.model.po.SysConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ConfigService {

    @Resource
    private RedisService redisService;

    public String getAIServerAddress() {
        SysConfig sysConfig = (SysConfig) redisService.getCacheObject(ConfigEnum.AI_SERVER_ADDRESS.name());
        return sysConfig.getValue();
    }

    public Integer getRagMaxDayCount() {
        SysConfig sysConfig = redisService.getConfig(ConfigEnum.RAG_MAX_DAY_COUNT);
        return Integer.valueOf(sysConfig.getValue());
    }

    public Long getHomeDocId() {
        SysConfig sysConfig = redisService.getConfig(ConfigEnum.HOME_DOC_ID);
        return Long.valueOf(sysConfig.getValue());
    }

    public String getAIServerAPIKey() {
        SysConfig sysConfig = redisService.getConfig(ConfigEnum.AI_SERVER_API_KEY);
        return sysConfig.getValue();
    }

    public String getMinIOConfig() {
        SysConfig sysConfig = redisService.getConfig(ConfigEnum.MIN_IO_CONFIG);
        return sysConfig.getValue();
    }

    public String getWhisperConfig() {
        SysConfig sysConfig = redisService.getConfig(ConfigEnum.WHISPER_CONFIG);
        return sysConfig.getValue();
    }

    public String getNacosWebUrl() {
        SysConfig sysConfig = redisService.getConfig(ConfigEnum.NACOS_WEB_URL);
        return sysConfig.getValue();
    }

    public String getXXLJobWebUrl() {
        SysConfig sysConfig = redisService.getConfig(ConfigEnum.XXL_JOB_WEB_URL);
        return sysConfig.getValue();
    }

    public String getKibanaWebUrl() {
        SysConfig sysConfig = redisService.getConfig(ConfigEnum.KIBANA_WEB_URL);
        return sysConfig.getValue();
    }

}
