package com.anynote.note.api.model.po;

import com.anynote.core.web.model.bo.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 笔记正文表
 * @author 称霸幼儿园
 */
@TableName("n_note_text ")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteText extends BaseEntity {

    /**
     * 笔记正文id
     */
    private Long id;

    /**
     * 笔记正文
     */
    private String content;

    @TableLogic
    @TableField("is_delete")
    private Integer deleted;
}
