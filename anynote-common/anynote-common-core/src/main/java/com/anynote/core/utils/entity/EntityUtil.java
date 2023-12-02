package com.anynote.core.utils.entity;

import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.BaseEntity;

import java.util.Date;

/**
 * @author 郑铖浩
 */
public class EntityUtil {

    /**
     * 设置实体基本信息
     * @param entity
     * @param creatorId
     * @param updaterId
     */
    public static void setBaseInfo(BaseEntity entity, Long creatorId, Long updaterId) {
        Date date = new Date();
        if (StringUtils.isNotNull(creatorId)) {
            entity.setCreateBy(creatorId);
            entity.setCreateTime(date);
        }
        if (StringUtils.isNotNull(updaterId)) {
            entity.setUpdateBy(updaterId);
            entity.setUpdateTime(date);
        }
    }
}
