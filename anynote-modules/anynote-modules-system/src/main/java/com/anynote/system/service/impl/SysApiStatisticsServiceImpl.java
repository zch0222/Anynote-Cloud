package com.anynote.system.service.impl;


import com.anynote.core.constant.SysApiStatisticsInterval;
import com.anynote.core.utils.DateUtils;
import com.anynote.system.api.model.dto.SysApiStatisticsListDTO;
import com.anynote.system.api.model.po.SysApiStatisticsPO;
import com.anynote.system.api.model.vo.SysApiStatisticsVO;
import com.anynote.system.mapper.SysApiStatisticsMapper;
import com.anynote.system.service.SysApiStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    public List<SysApiStatisticsVO> getSysApiStatistics(SysApiStatisticsListDTO sysApiStatisticsListDTO) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<SysApiStatisticsPO>()
                        .eq(SysApiStatisticsPO::getType, sysApiStatisticsListDTO.getType())
                        .eq(SysApiStatisticsPO::getStatisticsInterval, sysApiStatisticsListDTO.getStatisticsInterval())
                        .ge(SysApiStatisticsPO::getStartTime, sysApiStatisticsListDTO.getStartTime())
                        .le(SysApiStatisticsPO::getEndTime, sysApiStatisticsListDTO.getEndTime()))
                .stream()
                .map(po -> SysApiStatisticsVO.builder()
                        .id(po.getId())
                        .startTime(po.getStartTime())
                        .endTime(po.getEndTime())
                        .usageCount(po.getUsageCount())
                        .type(po.getType())
                        .statisticsInterval(po.getStatisticsInterval())
                        .createTime(po.getCreateTime())
                        .updateTime(po.getUpdateTime())
                        .build())
                .collect(Collectors.toList());
    }
}
