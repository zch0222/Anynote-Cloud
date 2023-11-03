package com.anynote.note.service.impl;

import com.alibaba.fastjson2.JSON;
import com.anynote.common.rocketmq.callback.RocketmqSendCallbackBuilder;
import com.anynote.common.rocketmq.properties.RocketMQProperties;
import com.anynote.common.rocketmq.tags.NoteTaskTagsEnum;
import com.anynote.note.api.model.po.NoteTaskOperationHistory;
import com.anynote.note.mapper.NoteTaskOperationHistoryMapper;
import com.anynote.note.model.vo.NoteTaskHistoryVO;
import com.anynote.note.service.NoteTaskOperationHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Service
public class NoteTaskOperationHistoryServiceImpl
        extends ServiceImpl<NoteTaskOperationHistoryMapper, NoteTaskOperationHistory>
        implements NoteTaskOperationHistoryService {

    @Resource
    private RocketMQProperties rocketMQProperties;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void asyncSaveNoteTaskOperationHistory(NoteTaskOperationHistory noteTaskOperationHistory) {
        String destination = rocketMQProperties.getNoteTaskTopic() + ":" + NoteTaskTagsEnum.INSERT_HISTORY.name();
        rocketMQTemplate.asyncSend(destination, JSON.toJSON(noteTaskOperationHistory), RocketmqSendCallbackBuilder.commonCallback());
    }

    @Override
    public List<NoteTaskHistoryVO> getNoteTaskHistoryList(Long userId, Long noteTaskId) {
        return this.baseMapper.selectNoteTaskHistoryList(userId, noteTaskId);
    }
}
