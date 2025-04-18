package com.anynote.manage.service.impl;


import com.anynote.ai.api.RemoteLlmStatisticsService;
import com.anynote.ai.api.model.dto.LlmStatisticsQueryDTO;
import com.anynote.ai.api.model.vo.StatisticsVO;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.utils.RemoteResDataUtil;
import com.anynote.manage.service.ManageApiStatisticsService;
import com.anynote.system.api.RemoteSysApiStatisticsService;
import com.anynote.system.api.model.dto.SysApiStatisticsListDTO;
import com.anynote.system.api.model.vo.SysApiStatisticsVO;
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

    @Resource
    private RemoteSysApiStatisticsService remoteSysApiStatisticsService;

    @Override
    public List<StatisticsVO> getLlmStatistics(LlmStatisticsQueryDTO llmStatisticsQueryDTO) {
        return RemoteResDataUtil.getResData(remoteLlmStatisticsService
                .getLlmStatistics(SecurityConstants.INNER, llmStatisticsQueryDTO));
    }

    @Override
    public List<SysApiStatisticsVO> getSysApiStatistics(SysApiStatisticsListDTO sysApiStatisticsListDTO) {
        return RemoteResDataUtil.getResData(remoteSysApiStatisticsService.getSysApiStatistics(sysApiStatisticsListDTO));
    }
}
