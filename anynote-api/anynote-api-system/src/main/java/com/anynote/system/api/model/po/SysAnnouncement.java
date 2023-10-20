package com.anynote.system.api.model.po;

import com.anynote.core.serialization.NullStringJsonSerializer;
import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@TableName("sys_announcement")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysAnnouncement extends BaseEntity {
    /**
     * 公告ID
     */
    private Long id;

    /**
     * 公告标题
     */
    @JsonSerialize(nullsUsing = NullStringJsonSerializer.class)
    private String title;

    /**
     * 公告内容
     */
    @JsonSerialize(nullsUsing = NullStringJsonSerializer.class)
    private String content;

    /**
     * 发布日期
     */
    private Date datePublished;

    /**
     * 发布者用户名
     */
    @JsonSerialize(nullsUsing = NullStringJsonSerializer.class)
    private String author;

    /**
     * 公告类别
     */
    @JsonSerialize(nullsUsing = NullStringJsonSerializer.class)
    private String type;

    /**
     * 是否置顶 (0表示否，1表示是)
     */
    @TableField("is_pinned")
    private int pinned;

    /**
     * 附件路径
     */
    @JsonSerialize(nullsUsing = NullStringJsonSerializer.class)
    private String attachment;

    @TableLogic
    @TableField("is_delete")
    private Integer deleted;

}
