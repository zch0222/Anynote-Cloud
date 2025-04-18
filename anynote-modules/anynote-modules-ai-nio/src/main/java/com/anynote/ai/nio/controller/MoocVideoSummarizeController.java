package com.anynote.ai.nio.controller;

import com.anynote.ai.api.model.dto.GetMoocVideoSummarizesByMoocIdDTO;
import com.anynote.ai.api.model.po.MoocVideoSummarizePO;
import com.anynote.ai.nio.service.MoocVideoSummarizeService;
import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("moocVideoSummarizes")
public class MoocVideoSummarizeController {

    @Resource
    private MoocVideoSummarizeService moocVideoSummarizeService;


    @InnerAuth
    @PostMapping("getMoocVideoSummarizesByMoocItemId")
    public Mono<ResData<List<MoocVideoSummarizePO>>> getMoocVideoSummarizesByMoocItemId(@RequestHeader(SecurityConstants.FROM_SOURCE) String source,
                                                                                        @RequestBody @Validated
                                                                                            GetMoocVideoSummarizesByMoocIdDTO getMoocVideoSummarizesByMoocIdDTO) {
        return Mono.fromCallable(() -> {
            log.info("getMoocVideoSummarizesByMoocItemId: {}", getMoocVideoSummarizesByMoocIdDTO.getMoocItemId());
            return ResUtil.success(moocVideoSummarizeService.list(new LambdaQueryWrapper<MoocVideoSummarizePO>()
                    .eq(MoocVideoSummarizePO::getMoocItemId, getMoocVideoSummarizesByMoocIdDTO.getMoocItemId())
                    .eq(MoocVideoSummarizePO::getMoocId, getMoocVideoSummarizesByMoocIdDTO.getMoocId())
                    .orderBy(true, true, MoocVideoSummarizePO::getCreateTime)));
        }).publishOn(Schedulers.boundedElastic());
    }
}
