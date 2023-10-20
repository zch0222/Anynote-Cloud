package com.anynote.note.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("n_note_history")
public class NoteHistory extends BaseEntity {

    /**
     * 历史记录ID
     */
    private Long id;

    /**
     * 操作ID
     */
    private Long operationId;

    /**
     * 笔记标题
     */
    private String title;

    /**
     * 笔记正文
     */
    private String content;

    /**
     * 历史时间
     */
    private Date historyTime;

    /**
     * 删除标志(0标识未删除 1表示删除)
     */
    @TableLogic
    @TableField("is_delete")
    private int deleted;

}
