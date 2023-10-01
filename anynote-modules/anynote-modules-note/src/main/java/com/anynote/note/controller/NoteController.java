package com.anynote.note.controller;

import com.anynote.common.datascope.annotation.DataScope;
import com.anynote.core.exception.user.UserParamException;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.api.model.po.Note;
import com.anynote.note.datascope.annotation.RequiresNotePermissions;
import com.anynote.note.enums.NotePermissions;
import com.anynote.note.model.bo.NoteCreateParam;
import com.anynote.note.model.bo.NoteDeleteParam;
import com.anynote.note.model.bo.NoteQueryParam;
import com.anynote.note.model.bo.NoteUpdateParam;
import com.anynote.note.model.dto.NoteCreateDTO;
import com.anynote.note.model.dto.NoteEditDTO;
import com.anynote.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 *
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("/notes")
@Validated
public class NoteController {

    @Autowired
    private NoteService noteService;

    @DataScope
    @PostMapping("bases/{baseId}")
    public ResData<PageBean<Note>> getNoteInfosByKnowledgeBaseId(@NotNull(message = "知识库id不能为空")
                                                                 @PathVariable Long baseId,
                                                                 @RequestBody NoteQueryParam queryParam) {
        if (StringUtils.isNull(queryParam.getPage())) {
            throw new UserParamException("页码不能为空", ResCode.USER_REQUEST_PARAM_ERROR);
        }
        if (StringUtils.isNull(queryParam.getPageSize())) {
            throw new UserParamException("页面大小不能为空", ResCode.USER_REQUEST_PARAM_ERROR);
        }
        queryParam.setKnowledgeBaseId(baseId);
        return ResUtil.success(noteService.getNotesByKnowledgeBaseId(queryParam));
    }

    @GetMapping("{noteId}")
    public ResData<Note> getNoteById(@NotNull(message = "笔记id不能为空")
                                     @PathVariable Long noteId) {
        NoteQueryParam queryParam = NoteQueryParam.builder()
                .id(noteId)
                .build();
        return ResUtil.success(noteService.getNoteById(queryParam));
    }

    @DeleteMapping("{noteId}")
    public ResData<String> deleteNote(@NotNull(message = "笔记id不能为空") @PathVariable Long noteId) {
        NoteDeleteParam noteDeleteParam = new NoteDeleteParam();
        noteDeleteParam.setId(noteId);
        return ResUtil.success(noteService.deleteNote(noteDeleteParam));
    }

    /**
     * 新建笔记
     * @param noteCreateDTO
     * @return
     */
    @PostMapping()
    public ResData<Long> createNote(@Validated @RequestBody NoteCreateDTO noteCreateDTO) {
        NoteCreateParam createParam = new NoteCreateParam(noteCreateDTO);
        return ResData.success(noteService.createNote(createParam));
    }


    /**
     * 更新笔记内容
     * @param note
     * @return
     */
    @PatchMapping("{noteId}")
    public ResData<String> editNote(@NotNull(message = "笔记id不能为空") @PathVariable Long noteId,
                                    @RequestBody NoteEditDTO noteEditDTO) {
        noteEditDTO.setNoteId(noteId);
        return ResUtil.success(noteService.editNote(new NoteUpdateParam(noteEditDTO)));
    }





}
