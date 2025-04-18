package com.anynote.ai.nio.controller;

import com.anynote.ai.api.model.dto.LlmStatisticsCreateDTO;
import com.anynote.ai.api.model.dto.LlmStatisticsQueryDTO;
import com.anynote.ai.api.model.po.LlmStatisticsPO;
import com.anynote.ai.api.model.vo.StatisticsVO;
import com.anynote.ai.nio.service.LlmStatisticsService;
import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("llmStatistics")
public class LlmStatisticsController {

    @Resource
    private LlmStatisticsService llmStatisticsService;

    @Resource
    private Gson gson;

    @InnerAuth
    @PostMapping("")
    public Mono<ResData<Long>> createLlmStatistics(@RequestHeader("from-source") String fromSource,
                                             @RequestBody @Validated LlmStatisticsCreateDTO llmStatisticsCreateDTO) {
        log.info("新建大模型调用统计: \n{}", gson.toJson(llmStatisticsCreateDTO));
        return Mono.fromCallable(() -> {
            LlmStatisticsPO llmStatisticsPO = LlmStatisticsPO.builder()
                    .startTime(llmStatisticsCreateDTO.getStartTime())
                    .endTime(llmStatisticsCreateDTO.getEndTime())
                    .type(llmStatisticsCreateDTO.getType())
                    .usageCount(0)
                    .deleted(0)
                    .build();
            llmStatisticsService.getBaseMapper().insert(llmStatisticsPO);
            return ResUtil.success(llmStatisticsPO.getId());
        }).publishOn(Schedulers.boundedElastic());
    }

    /**
     * 查询调用统计
     * @param fromSource
     * @param llmStatisticsQueryDTO
     * @return
     */
    @InnerAuth
    @GetMapping("list")
    public Mono<ResData<List<StatisticsVO>>> getLlmStatistics(@RequestHeader("from-source") String fromSource,
                                                              @Validated LlmStatisticsQueryDTO llmStatisticsQueryDTO) {
        return llmStatisticsService.getLlmStatistics(llmStatisticsQueryDTO)
                .flatMap(llmStatisticsVOS -> Mono.just(ResUtil.success(llmStatisticsVOS)));
    }
}
