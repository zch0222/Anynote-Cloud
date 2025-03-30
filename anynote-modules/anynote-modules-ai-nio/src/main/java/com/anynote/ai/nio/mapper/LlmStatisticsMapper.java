package com.anynote.ai.nio.mapper;

import com.anynote.ai.api.model.po.LlmStatisticsPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Mapper
public interface LlmStatisticsMapper extends BaseMapper<LlmStatisticsPO> {

    /**
     * 调用次数+1
     * @param startTime
     * @param endTime
     * @return
     */
    public int increaseUsageCount(@Param("startTime")Date startTime, @Param("endTime")Date endTime);
}
