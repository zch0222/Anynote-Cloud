package com.anynote.note.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;
import java.util.Map;

/**
 * @author 称霸幼儿园
 */
@EqualsAndHashCode(callSuper = true)
@TableName("n_mooc_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoocItemPO extends BaseEntity {
    /**
     * 慕课 Item id
     */
    private Long id;

    /**
     * 慕课id
     */
    private Long moocId;

    /**
     * 慕课Item标题
     */
    private String title;

    /**
     * 慕课类型对象 0.章节 1.视频 2.文档
     */
    private Integer moocItemType;

    /**
     * 文件对象名称
     */
    private String objectName;

    /**
     * 父Item id，如果为0表示没有父节点
     */
    private Long parentId;

    /**
     * 删除标志(0标识未删除 1表示删除)
     */
    @TableField("is_delete")
    @TableLogic
    private Integer deleted;

    @Builder
    public MoocItemPO(Long id, Long moocId, String title, Integer moocItemType, String objectName, Long parentId, Integer deleted, Long createBy,
                      Date createTime, Long updateBy, Date updateTime, String remark, Map<String, Object> params) {
        super(createBy, createTime, updateBy, updateTime, remark, params);
        this.id = id;
        this.moocId = moocId;
        this.title = title;
        this.moocItemType = moocItemType;
        this.objectName = objectName;
        this.parentId = parentId;
        this.deleted = deleted;
    }
}