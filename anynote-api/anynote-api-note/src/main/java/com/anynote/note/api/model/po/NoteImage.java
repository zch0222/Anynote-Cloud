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
 * 笔记图片文件表
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("f_note_image")
public class NoteImage extends BaseEntity {
    /**
     * 图片id
     */
    private Long id;

    private String originalFileName;

    private String fileName;

    private String url;

    private Long userId;

    @TableLogic
    @TableField("is_delete")
    private Integer deleted;
}
