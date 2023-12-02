package com.anynote.note.api.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 笔记文件关联表
 * @author 称霸幼儿园
 */
@Data
@Builder
@TableName("n_note_file")
@NoArgsConstructor
@AllArgsConstructor
public class NoteFile {

    /**
     * 笔记id
     */
    private Long noteId;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 文件类型 0.图片
     */
    private Integer type;
}
