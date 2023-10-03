package com.anynote.note.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteTaskQueryParam extends KnowledgeBaseQueryParam {

    /**
     * 笔记任务id
     */
    private Long noteTaskId;

    private Long submitUserId;
}
