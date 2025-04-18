package com.anynote.system.api.factory;

import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.system.api.RemoteSysApiStatisticsService;
import com.anynote.system.api.model.dto.ApiStatisticsCreateDTO;
import com.anynote.system.api.model.dto.IncreaseApiUsageDTO;
import com.anynote.system.api.model.dto.SysApiStatisticsListDTO;
import com.anynote.system.api.model.vo.SysApiStatisticsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RemoteSysApiStatisticsFallbackFactory implements FallbackFactory<RemoteSysApiStatisticsService> {

    @Override
    public RemoteSysApiStatisticsService create(Throwable cause) {
        return new RemoteSysApiStatisticsService() {
            @Override
            public ResData<Long> createApiStatistics(ApiStatisticsCreateDTO apiStatisticsCreateDTO) {
                throw new BusinessException("调用/apiStatistics POST失败");
            }

            @Override
            public ResData<Long> createApiStatistics(ApiStatisticsCreateDTO apiStatisticsCreateDTO, String fromSource) {
                throw new BusinessException("调用/apiStatistics POST失败");
            }

            @Override
            public ResData<String> increaseUsage(IncreaseApiUsageDTO increaseApiUsageDTO) {
                throw new BusinessException("调用/apiStatistics/increaseUsage POST失败");
            }

            @Override
            public ResData<String> increaseUsage(IncreaseApiUsageDTO increaseApiUsageDTO, String fromSource) {
                throw new BusinessException("调用/apiStatistics/increaseUsage POST失败");
            }


            @Override
            public ResData<List<SysApiStatisticsVO>> getSysApiStatistics(SysApiStatisticsListDTO sysApiStatisticsListDTO) {
                throw new BusinessException("调用/apiStatistics GET失败");
            }
        };
    }
}
