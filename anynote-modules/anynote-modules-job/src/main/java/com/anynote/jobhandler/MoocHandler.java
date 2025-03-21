package com.anynote.jobhandler;

import com.anynote.model.MoocVideoARSParam;
import com.google.gson.Gson;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 慕课任务执行器
 * @author 称霸幼儿园
 */
@lombok.extern.slf4j.Slf4j
@Slf4j
@Component
public class MoocHandler {

    @Resource
    private Gson gson;

    /**
     * 慕课语音识别
     */
    @XxlJob("moocVideoARS")
    public void moocVideoARS() {
        MoocVideoARSParam param = gson.fromJson(XxlJobHelper.getJobParam(), MoocVideoARSParam.class);
        log.info("moocVideoARS: {}", param.getMoocItemId());
    }
}
