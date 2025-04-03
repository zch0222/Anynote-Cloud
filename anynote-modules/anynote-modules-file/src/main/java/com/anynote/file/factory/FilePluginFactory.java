package com.anynote.file.factory;

import com.alibaba.fastjson2.JSON;
import com.anynote.common.redis.service.ConfigService;
import com.anynote.common.redis.service.RedisService;
import com.anynote.core.enums.ConfigEnum;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.web.enums.ResCode;
import com.anynote.file.enums.OssTypeEnum;
import com.anynote.file.model.bo.HuaweiOBSConfig;
import com.anynote.file.model.bo.MinIOConfig;
import com.anynote.file.plugin.FilePlugin;
import com.anynote.file.plugin.impl.HuaweiFilePlugin;
import com.anynote.file.plugin.impl.MinIOFilePlugin;
import com.anynote.system.api.model.po.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 文件插件工厂
 * @author 称霸幼儿园
 */
@Component
public class FilePluginFactory {

    @Autowired
    private RedisService redisService;

    @Resource
    private ConfigService configService;

    public FilePlugin filePlugin() {
        switch (OssTypeEnum.valueOf(((SysConfig) redisService.getCacheObject(ConfigEnum.OSS_TYPE.name())).getValue())) {
            case HUAWEI_OBS: {
                HuaweiOBSConfig huaweiOBSConfig = JSON.parseObject(((SysConfig)
                                redisService.getCacheObject(ConfigEnum.HUAWEI_OBS_CONFIG.name())).getValue(),
                        HuaweiOBSConfig.class);
                return new HuaweiFilePlugin(huaweiOBSConfig);
            }
            case MIN_IO: {
                MinIOConfig minIOConfig = JSON.parseObject(configService.getMinIOConfig(), MinIOConfig.class);
                return new MinIOFilePlugin(minIOConfig);
            }
            default:
                throw new BusinessException("文件上传失败", ResCode.CALLING_SERVICE_ERROR);
        }
    }

    public HuaweiFilePlugin huaweiFilePlugin() {
        HuaweiOBSConfig huaweiOBSConfig = JSON.parseObject(((SysConfig)
                        redisService.getCacheObject(ConfigEnum.HUAWEI_OBS_CONFIG.name())).getValue(),
                HuaweiOBSConfig.class);
        return new HuaweiFilePlugin(huaweiOBSConfig);
//        if (OssTypeEnum.HUAWEI_OBS.equals(OssTypeEnum.valueOf(((SysConfig) redisService
//                .getCacheObject(ConfigEnum.OSS_TYPE.name())).getValue()))) {
//            HuaweiOBSConfig huaweiOBSConfig = JSON.parseObject(((SysConfig)
//                            redisService.getCacheObject(ConfigEnum.HUAWEI_OBS_CONFIG.name())).getValue(),
//                    HuaweiOBSConfig.class);
//            return new HuaweiFilePlugin(huaweiOBSConfig);
//        }
//        else {
//            throw new BusinessException("文件上传失败", ResCode.CALLING_SERVICE_ERROR);
//        }
    }

}
