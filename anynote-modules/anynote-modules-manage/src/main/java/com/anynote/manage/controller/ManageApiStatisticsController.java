package com.anynote.manage.controller;


import com.anynote.ai.api.model.dto.LlmStatisticsQueryDTO;
import com.anynote.ai.api.model.vo.StatisticsVO;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.manage.service.ManageApiStatisticsService;
import com.anynote.system.api.model.dto.SysApiStatisticsListDTO;
import com.anynote.system.api.model.vo.SysApiStatisticsVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 第三方调用API统计
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("apiStatistics")
public class ManageApiStatisticsController {


    @Resource
    private ManageApiStatisticsService manageApiStatisticsService;

    /**
     * 获取LLM API调用统计
     * @param llmStatisticsQueryDTO
     * @return
     */
    @GetMapping("llmStatistics")
    public ResData<List<StatisticsVO>> getLlmStatistics(LlmStatisticsQueryDTO llmStatisticsQueryDTO) {
        return ResUtil.success(manageApiStatisticsService.getLlmStatistics(llmStatisticsQueryDTO));
    }

    /**
     * 获取API调用统计
     * @param sysApiStatisticsListDTO
     * @return
     */
    @GetMapping("")
    public ResData<List<SysApiStatisticsVO>> getSysApiStatistics(@Validated SysApiStatisticsListDTO sysApiStatisticsListDTO) {
        return ResUtil.success(manageApiStatisticsService.getSysApiStatistics(sysApiStatisticsListDTO));
    }
}
