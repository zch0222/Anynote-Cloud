package com.anynote.system.listener;

import com.anynote.canal.constants.CanalMessageType;
import com.anynote.canal.model.bo.CanalMessage;
import com.anynote.common.redis.service.ConfigService;
import com.anynote.common.redis.service.RedisService;
import com.anynote.core.enums.ConfigEnum;
import com.anynote.system.api.model.po.SysConfig;
import com.anynote.system.service.SysConfigService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订阅sys_config表变化
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.canal-topic}",
        consumerGroup = "${anynote.data.rocketmq.canal-system-config-group}",
        maxReconsumeTimes = 1,
        messageModel = MessageModel.CLUSTERING
)
public class CanalConfigListener implements RocketMQListener<MessageExt> {

    private final String SYS_CONFIG_TABLE_NAME = "sys_config";

    @Resource
    private RedisService redisService;

    @Override
    public void onMessage(MessageExt messageExt) {
        CanalMessage canalMessage = new CanalMessage(messageExt.getBody());
        // 涉及的表不是sys_config，则不做处理
        if (!SYS_CONFIG_TABLE_NAME.equals(canalMessage.getTableName())) {
            return;
        }
        log.info("sys_config表变化:\n {}", canalMessage.getMessageSrt());
        // 如果涉及的数据库操作不是UPDATE，则不做处理
        if (!CanalMessageType.UPDATE.equals(canalMessage.getType())) {
            return;
        }
        List<SysConfig> sysConfigList = canalMessage.getData(SysConfig.class);
        for (SysConfig sysConfig : sysConfigList) {
            log.info("更新Config：{}\nvalue:\n{}", sysConfig.getName(), sysConfig.getValue());
            redisService.setCacheObject(ConfigEnum.valueOf(sysConfig.getName()).name(), sysConfig);
        }
    }
}
