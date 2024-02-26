package com.anynote.note.model.po;

import lombok.Data;

@Data
public class NoteTaskAnalyzePO {
    private String username;
    private String nickname;
    private String taskName;
    private String knowledgeBaseName;
    private Long noteTaskId;
    private Long noteId;
    private Long editCount;
}
