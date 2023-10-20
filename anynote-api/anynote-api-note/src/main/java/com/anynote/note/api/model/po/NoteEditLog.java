package com.anynote.note.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("n_note_edit_log")
public class NoteEditLog extends BaseEntity {
    /**
     * 编辑记录id
     */
    private long id;

    /**
     * 操作id
     */
    private long operationId;

    /**
     * 原始文本
     */
    private String originalText;

    /**
     * 修改后的文本
     */
    private String revisedText;

    /**
     * 修改类型 0.修改行 1.删除行 2.插入行
     */
    private int changeType;

    /**
     * 原始修改位置，行号从0开始
     */
    private int originalPosition;

    /**
     * 新行号位置
     */
    private int revisedPosition;

    /**
     * 删除标志 (0标识未删除 1表示删除)
     */
    @TableLogic
    @TableField("is_delete")
    private boolean deleted;
}
