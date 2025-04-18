package com.anynote.system.api;

import com.anynote.core.constant.ServiceNameConstants;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.system.api.factory.RemoteSysApiStatisticsFallbackFactory;
import com.anynote.system.api.model.dto.ApiStatisticsCreateDTO;
import com.anynote.system.api.model.dto.IncreaseApiUsageDTO;
import com.anynote.system.api.model.dto.SysApiStatisticsListDTO;
import com.anynote.system.api.model.vo.SysApiStatisticsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "remoteSysApiStatisticsService",
        value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteSysApiStatisticsFallbackFactory.class)
public interface RemoteSysApiStatisticsService {

    /**
     * 创建统计日志
     * @param apiStatisticsCreateDTO
     * @return
     */
    @PostMapping("apiStatistics")
    public ResData<Long> createApiStatistics(@Validated @RequestBody ApiStatisticsCreateDTO apiStatisticsCreateDTO);

    /**
     * 创建统计日志
     * @param apiStatisticsCreateDTO
     * @return
     */
    @PostMapping("apiStatistics")
    public ResData<Long> createApiStatistics(@Validated @RequestBody ApiStatisticsCreateDTO apiStatisticsCreateDTO,
                                             @RequestHeader("from-source") String fromSource);

    /**
     * 增加调用
     * @param increaseApiUsageDTO
     * @return
     */
    @PostMapping("apiStatistics/increaseUsage")
    public ResData<String> increaseUsage(@Validated @RequestBody IncreaseApiUsageDTO increaseApiUsageDTO);

    /**
     * 增加调用
     * @param increaseApiUsageDTO
     * @param fromSource
     * @return
     */
    @PostMapping("apiStatistics/increaseUsage")
    public ResData<String> increaseUsage(@Validated @RequestBody IncreaseApiUsageDTO increaseApiUsageDTO,
                                         @RequestHeader("from-source") String fromSource);

    @PostMapping("apiStatistics/getApiStatistics")
    public ResData<List<SysApiStatisticsVO>> getSysApiStatistics(@RequestBody SysApiStatisticsListDTO sysApiStatisticsListDTO);
}
