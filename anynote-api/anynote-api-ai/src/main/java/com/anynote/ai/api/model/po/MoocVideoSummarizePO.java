package com.anynote.ai.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;
import java.util.Map;

/**
 * 慕课视频摘要记录实体
 *
 * @author 称霸幼儿园
 */
@Data
@TableName("a_mooc_video_summarize")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MoocVideoSummarizePO extends BaseEntity {
    /**
     * 记录ID
     */
    private Long id;

    /**
     * 慕课id
     */
    private Long moocId;

    /**
     * 慕课item id
     */
    private Long moocItemId;

    /**
     * 摘要内容
     */
    private String content;

    /**
     * 使用的模型名称
     */
    private String model;

    /**
     * 删除标记 0正常 1删除
     */
    @TableField("is_delete")
    @TableLogic
    private Integer deleted;

    @Builder
    public MoocVideoSummarizePO(Long id, Long moocId, Long moocItemId, String content, String model, Integer deleted,
                              Long createBy, Date createTime, Long updateBy, Date updateTime,
                              String remark, Map<String, Object> params) {
        super(createBy, createTime, updateBy, updateTime, remark, params);
        this.id = id;
        this.moocId = moocId;
        this.moocItemId = moocItemId;
        this.content = content;
        this.model = model;
        this.deleted = deleted;
    }

}
