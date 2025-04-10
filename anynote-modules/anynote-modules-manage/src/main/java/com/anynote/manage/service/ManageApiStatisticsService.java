package com.anynote.manage.service;

import com.anynote.ai.api.model.dto.LlmStatisticsQueryDTO;
import com.anynote.ai.api.model.vo.LlmStatisticsVO;

import java.util.List;

public interface ManageApiStatisticsService {

    /**
     * 获取大语言模型调用统计
     * @param llmStatisticsQueryDTO
     * @return
     */

    public List<LlmStatisticsVO> getLlmStatistics(LlmStatisticsQueryDTO llmStatisticsQueryDTO);
}
