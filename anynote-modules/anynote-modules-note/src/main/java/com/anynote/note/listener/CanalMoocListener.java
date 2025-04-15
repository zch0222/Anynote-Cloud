package com.anynote.note.listener;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.anynote.canal.constants.CanalMessageType;
import com.anynote.canal.model.bo.CanalMessage;
import com.anynote.common.elasticsearch.constant.ElasticsearchIndexConstants;
import com.anynote.common.elasticsearch.model.bo.EsMoocIndex;
import com.anynote.note.model.po.MoocPO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 慕课变更监听
 * @author 称霸幼儿园
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${anynote.data.rocketmq.canal-topic}",
        consumerGroup = "${anynote.data.rocketmq.canal-mooc-group}", maxReconsumeTimes = 5,
        messageModel = MessageModel.CLUSTERING
)
public class CanalMoocListener implements RocketMQListener<MessageExt> {

    @Resource
    private Gson gson;

    @Resource
    private ElasticsearchClient elasticsearchClient;

    private final String MOOC_TABLE_NAME = "n_mooc";

    @Override
    public void onMessage(MessageExt messageExt) {
        CanalMessage canalMessage = new CanalMessage(messageExt.getBody());

        if (!MOOC_TABLE_NAME.equals(canalMessage.getTableName())) {
            return;
        }
        log.info(gson.toJson(messageExt));
        log.info(new String(messageExt.getBody(), StandardCharsets.UTF_8));
        if (CanalMessageType.UPDATE.equals(canalMessage.getType()) ||
                CanalMessageType.INSERT.equals(canalMessage.getType())) {
            List<MoocPO> moocPOList = canalMessage.getData(MoocPO.class);
            for (MoocPO moocPO : moocPOList) {
                buildESIndex(moocPO);
            }
        }
    }


    private void buildESIndex(MoocPO moocPO) {

        EsMoocIndex esMoocIndex = EsMoocIndex.builder()
                .id(moocPO.getId())
                .title(moocPO.getTitle())
                .knowledgeBaseId(moocPO.getKnowledgeBaseId())
                .dataScope(moocPO.getDataScope())
                .permissions(moocPO.getPermissions())
                .deleted(moocPO.getDeleted())
                .createBy(moocPO.getCreateBy())
                .createTime(moocPO.getCreateTime())
                .updateBy(moocPO.getUpdateBy())
                .updateTime(moocPO.getUpdateTime())
                .build();
        log.info("Mooc Index Value: {}", gson.toJson(esMoocIndex));
        IndexResponse response = null;
        try {
            response = elasticsearchClient.index(i -> i
                    .index(ElasticsearchIndexConstants.MOOC_INDEX)
                    .id(String.valueOf(esMoocIndex.getId()))
                    .document(esMoocIndex));
        } catch (IOException e) {
            log.error("创建慕课索引失败，id: {}", esMoocIndex.getId(), e);
            throw new RuntimeException(e);
        }
        log.info("创建慕课索引成功，id: {}, version: {}", esMoocIndex.getId(), response.version());
    }
}
