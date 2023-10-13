package com.anynote.note.model.vo;

import com.anynote.note.api.model.po.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 笔记信息VO
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteInfoVO extends Note {

    /**
     * 所属知识库名称
     */
    private String knowledgeBaseName;


    /**
     * 笔记创建者姓名
     */
    private String creatorName;



}
