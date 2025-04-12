package com.anynote.system.mapper;

import com.anynote.system.api.model.po.SysApiStatisticsPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Mapper
public interface SysApiStatisticsMapper extends BaseMapper<SysApiStatisticsPO> {

    /**
     * 调用次数+1
     * @param startTime
     * @param endTime
     * @return
     */
    public int increaseUsageCount(@Param("startTime")Date startTime, @Param("endTime")Date endTime,
                                  @Param("type")Integer type, @Param("statisticsInterval") Integer statisticsInterval);
}
