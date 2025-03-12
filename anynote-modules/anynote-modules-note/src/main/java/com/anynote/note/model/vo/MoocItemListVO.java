package com.anynote.note.model.vo;

import com.anynote.note.model.po.MoocItemPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 慕课ItemVO
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoocItemListVO {

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

    /** 创建者 */
    private Long createBy;

    /** 创建时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
    private Date updateTime;

    public MoocItemListVO(MoocItemPO moocItemPO) {
        this.id = moocItemPO.getId();
        this.moocId = moocItemPO.getMoocId();
        this.title = moocItemPO.getTitle();
        this.moocItemType = moocItemPO.getMoocItemType();
        this.objectName = moocItemPO.getObjectName();
        this.parentId = moocItemPO.getParentId();
        this.createBy = moocItemPO.getCreateBy();
        this.createTime = moocItemPO.getCreateTime();
        this.updateBy = moocItemPO.getUpdateBy();
        this.updateTime = moocItemPO.getUpdateTime();
    }

}
