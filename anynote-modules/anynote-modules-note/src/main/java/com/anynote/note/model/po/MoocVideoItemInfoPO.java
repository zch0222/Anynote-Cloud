package com.anynote.note.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 慕课视频信息
 *
 * @author 称霸幼儿园
 */
@TableName("n_mooc_video_item_info")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoocVideoItemInfoPO extends BaseEntity {

    /**
     * 慕课视频信息id
     */
    private Long id;

    /**
     * 慕课id
     */
    private Long moocId;

    /**
     * 慕课
     */
    private Long moocItemId;

    /**
     * 字幕对象名称
     */
    private String srtObjectName;

    /**
     * 总结
     */
    private String videoSummarize;

    /**
     * 删除标志(0标识未删除 1表示删除)
     */
    @TableField("is_delete")
    @TableLogic
    private Integer deleted;

    @Builder
    public MoocVideoItemInfoPO(Long id, Long moocId, Long moocItemId, String srtObjectName, String videoSummarize,
                             Integer deleted, Long createBy, Date createTime, Long updateBy, Date updateTime,
                             String remark, Map<String, Object> params) {
        super(createBy, createTime, updateBy, updateTime, remark, params);
        this.id = id;
        this.moocId = moocId;
        this.moocItemId = moocItemId;
        this.srtObjectName = srtObjectName;
        this.videoSummarize = videoSummarize;
        this.deleted = deleted;
    }
}
