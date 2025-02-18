package com.anynote.note.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;
import java.util.Map;

/**
 * 慕课Item文本PO
 * @author 称霸幼儿园
 */
@TableName("n_mooc_item_text")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoocItemTextPO extends BaseEntity {
    /**
     * 慕课item文本id
     */
    private Long id;

    /**
     * 慕课item id
     */
    private Long moocItemId;

    /**
     * 慕课item文本内容
     */
    private String itemText;

    /**
     * 删除标志(0标识未删除 1表示删除)
     */
    @TableField("is_delete")
    @TableLogic
    private Integer deleted;

    @Builder
    public MoocItemTextPO(Long id, Long moocItemId, String itemText, Integer deleted,
                          Long createBy, Date createTime, Long updateBy, Date updateTime,
                          String remark, Map<String, Object> params) {
        super(createBy, createTime, updateBy, updateTime, remark, params);
        this.id = id;
        this.moocItemId = moocItemId;
        this.itemText = itemText;
        this.deleted = deleted;
    }
}
