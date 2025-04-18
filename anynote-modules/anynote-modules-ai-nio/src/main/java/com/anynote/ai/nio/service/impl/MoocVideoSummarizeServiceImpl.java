package com.anynote.ai.nio.service.impl;

import com.anynote.ai.api.model.po.MoocVideoSummarizePO;
import com.anynote.ai.nio.mapper.MoocVideoSummarizeMapper;
import com.anynote.ai.nio.service.MoocVideoSummarizeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MoocVideoSummarizeServiceImpl extends ServiceImpl<MoocVideoSummarizeMapper, MoocVideoSummarizePO>
        implements MoocVideoSummarizeService {
}
