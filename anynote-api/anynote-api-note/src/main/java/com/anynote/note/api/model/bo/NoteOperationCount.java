package com.anynote.note.api.model.bo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteOperationCount
{
    private Long userId;

    private String nickname;

    private Long noteId;

    private Long count;
}
