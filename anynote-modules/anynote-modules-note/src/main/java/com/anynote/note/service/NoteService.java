package com.anynote.note.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.api.model.po.Note;
import com.anynote.note.datascope.annotation.RequiresKnowledgeBasePermissions;
import com.anynote.note.datascope.annotation.RequiresNotePermissions;
import com.anynote.note.enums.KnowledgeBasePermissions;
import com.anynote.note.enums.NotePermissions;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.dto.NoteCreateDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 笔记服务
 * @author 称霸幼儿园
 */
public interface NoteService extends IService<Note> {

    public PageBean<Note> getNotesByKnowledgeBaseId(NoteQueryParam queryParam);

    public Note getNoteById(NoteQueryParam queryParam);

    public String editNote(NoteUpdateParam updateParam);

    public Integer getNoteDataScope(Long noteId);

    public String deleteNote(NoteDeleteParam param);

    /**
     * 创建笔记
     * @return 新笔记的id
     */
    public Long createNote(NoteCreateParam param);

    public NotePermissions getNotePermissions(Long noteId);

    public MarkdownImage uploadNoteImage(NoteImageUploadParam uploadParam);

    public Integer submitNote(Long noteId);
}
