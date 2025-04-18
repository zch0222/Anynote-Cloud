package com.anynote.system.service;


import com.anynote.system.api.model.dto.SysApiStatisticsListDTO;
import com.anynote.system.api.model.po.SysApiStatisticsPO;
import com.anynote.system.api.model.vo.SysApiStatisticsVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * @author 称霸幼儿园
 */
public interface SysApiStatisticsService extends IService<SysApiStatisticsPO> {

    /**
     * 增加调用次数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param type 统计类型
     * @param statisticsInterval 统计间隔
     */
    public void increaseUsageCount(Date startTime, Date endTime,
                                   Integer type, Integer statisticsInterval);

    /**
     * 增加调用此时
     * @param time 调用时间
     * @param type API类型
     */
    public void increaseUsageCount(Date time, Integer type);


    /**
     * 获取调用统计
     * @param sysApiStatisticsListDTO
     * @return
     */
    public List<SysApiStatisticsVO> getSysApiStatistics(SysApiStatisticsListDTO sysApiStatisticsListDTO);



}
