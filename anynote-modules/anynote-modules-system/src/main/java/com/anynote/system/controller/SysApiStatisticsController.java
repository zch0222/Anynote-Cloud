package com.anynote.system.controller;

import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.constant.Constants;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.system.api.model.dto.ApiStatisticsCreateDTO;
import com.anynote.system.api.model.dto.IncreaseApiUsageDTO;
import com.anynote.system.api.model.po.SysApiStatisticsPO;
import com.anynote.system.service.SysApiStatisticsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * API 用量统计
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("apiStatistics")
public class SysApiStatisticsController {

    @Resource
    private SysApiStatisticsService sysApiStatisticsService;

    @InnerAuth
    @PostMapping
    public ResData<Long> createApiStatistics(@Validated @RequestBody ApiStatisticsCreateDTO apiStatisticsCreateDTO) {
        Date now = new Date();
        SysApiStatisticsPO sysApiStatisticsPO = SysApiStatisticsPO.builder()
                .startTime(apiStatisticsCreateDTO.getStartTime())
                .endTime(apiStatisticsCreateDTO.getEndTime())
                .type(apiStatisticsCreateDTO.getType())
                .statisticsInterval(apiStatisticsCreateDTO.getStatisticsInterval())
                .createTime(now)
                .updateTime(now)
                .deleted(0)
                .build();
        sysApiStatisticsService.save(sysApiStatisticsPO);
        return ResUtil.success(sysApiStatisticsPO.getId());
    }

    @InnerAuth
    @PostMapping("increaseUsage")
    public ResData<String> increaseUsage(@Validated @RequestBody IncreaseApiUsageDTO increaseApiUsageDTO) {
        sysApiStatisticsService.increaseUsageCount(increaseApiUsageDTO.getTime(), increaseApiUsageDTO.getType());
        return ResUtil.success(Constants.SUCCESS_RES);
    }

}
