package com.anynote.manage.controller;

import com.anynote.common.redis.service.ConfigService;
import com.anynote.common.redis.service.RedisService;
import com.anynote.core.enums.ConfigEnum;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("sysWeb")
public class ManageSysWebController {

    @Resource
    private RedisService redisService;


    /**
     * 获取web url
     * @param web
     * @return
     */
    @GetMapping("")
    public ResData<String> getWebUrl(String web) {
        if (!Arrays.asList("NACOS_WEB_URL", "XXL_JOB_WEB_URL", "KIBANA_WEB_URL").contains(web)) {
            throw new BusinessException("参数错误");
        }
        return ResUtil.success(redisService.getConfig(ConfigEnum.valueOf(web)).getValue());
    }
}
