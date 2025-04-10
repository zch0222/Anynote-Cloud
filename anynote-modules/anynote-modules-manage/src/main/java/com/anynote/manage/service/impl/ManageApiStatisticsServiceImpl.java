package com.anynote.manage.service.impl;


import com.anynote.ai.api.RemoteLlmStatisticsService;
import com.anynote.ai.api.model.dto.LlmStatisticsQueryDTO;
import com.anynote.ai.api.model.vo.LlmStatisticsVO;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.utils.RemoteResDataUtil;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.manage.service.ManageApiStatisticsService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Slf4j
@Service
public class ManageApiStatisticsServiceImpl implements ManageApiStatisticsService {


    @Resource
    private RemoteLlmStatisticsService remoteLlmStatisticsService;

    @Override
    public List<LlmStatisticsVO> getLlmStatistics(LlmStatisticsQueryDTO llmStatisticsQueryDTO) {
        log.info(new Gson().toJson(llmStatisticsQueryDTO));
        log.info("UUUUUU");
        return RemoteResDataUtil.getResData(remoteLlmStatisticsService
                .getLlmStatistics(SecurityConstants.INNER, llmStatisticsQueryDTO));
    }
}
