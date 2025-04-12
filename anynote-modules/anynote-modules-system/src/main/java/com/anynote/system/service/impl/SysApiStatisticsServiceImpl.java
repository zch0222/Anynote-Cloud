package com.anynote.system.service.impl;


import com.anynote.core.constant.SysApiStatisticsInterval;
import com.anynote.core.utils.DateUtils;
import com.anynote.system.api.model.po.SysApiStatisticsPO;
import com.anynote.system.mapper.SysApiStatisticsMapper;
import com.anynote.system.service.SysApiStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysApiStatisticsServiceImpl extends ServiceImpl<SysApiStatisticsMapper, SysApiStatisticsPO>
        implements SysApiStatisticsService {

    @Override
    public void increaseUsageCount(Date startTime, Date endTime,
                                   Integer type, Integer statisticsInterval) {
        baseMapper.increaseUsageCount(startTime, endTime, type, statisticsInterval);
    }

    @Override
    public void increaseUsageCount(Date time, Integer type) {
        // 添加分钟usage
        increaseUsageCount(DateUtils.getStartOfMinute(time),
                DateUtils.getEndOfMinute(time), type,
                SysApiStatisticsInterval.MINUTE);
        // 添加分钟usage
        increaseUsageCount(DateUtils.getStartOfHour(time),
                DateUtils.getEndOfHour(time), type,
                SysApiStatisticsInterval.HOUR);
        // 添加天
        increaseUsageCount(DateUtils.getStartOfDay(time),
                DateUtils.getEndOfDay(time), type,
                SysApiStatisticsInterval.DAY);
    }
}
