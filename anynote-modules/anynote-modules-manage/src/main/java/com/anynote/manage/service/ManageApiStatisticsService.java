package com.anynote.manage.service;

import com.anynote.ai.api.model.dto.LlmStatisticsQueryDTO;
import com.anynote.ai.api.model.vo.StatisticsVO;
import com.anynote.system.api.model.dto.SysApiStatisticsListDTO;
import com.anynote.system.api.model.vo.SysApiStatisticsVO;

import java.util.List;

public interface ManageApiStatisticsService {

    /**
     * 获取大语言模型调用统计
     * @param llmStatisticsQueryDTO
     * @return
     */

    public List<StatisticsVO> getLlmStatistics(LlmStatisticsQueryDTO llmStatisticsQueryDTO);

    /**
     * 获取API分析记录
     * @param sysApiStatisticsListDTO
     * @return
     */
    public List<SysApiStatisticsVO> getSysApiStatistics(SysApiStatisticsListDTO sysApiStatisticsListDTO);
}
