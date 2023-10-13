package com.anynote.note.controller;

import com.anynote.core.constant.ErrorMessageConstants;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.CreateResEntity;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.model.bo.NoteTaskCreateParam;
import com.anynote.note.model.bo.NoteTaskQueryParam;
import com.anynote.note.model.bo.NoteTaskSubmitParam;
import com.anynote.note.model.dto.MemberNoteTaskDTO;
import com.anynote.note.model.dto.NoteTaskSubmissionRecordCreateDTO;
import com.anynote.note.model.dto.NoteTaskCreateDTO;
import com.anynote.note.service.NoteTaskService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 笔记任务 Controller
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("noteTasks")
@Validated
public class NoteTaskController {

    @Autowired
    private NoteTaskService noteTaskService;

//    @PostMapping
//    public ResData<CreateResEntity> createNoteTask(@RequestBody @Valid NoteTaskCreateDTO noteTaskCreateDTO) {
//        Long noteTaskId = noteTaskService.createNoteTask(new NoteTaskCreateParam(noteTaskCreateDTO));
//        return ResUtil.success(CreateResEntity.builder()
//                .id(noteTaskId)
//                .build());
//    }

    @PostMapping("submit")
    public ResData<String> submitNoteTask(@RequestBody @Validated NoteTaskSubmissionRecordCreateDTO noteTaskSubmissionRecordCreateDTO) {
        return ResUtil.success(noteTaskService
                .submitNoteTask(new NoteTaskSubmitParam(noteTaskSubmissionRecordCreateDTO)));
    }

    @GetMapping
    public ResData<PageBean<MemberNoteTaskDTO>> getMemberNoteTasks(@NotNull(message = "请选择知识库") Long knowledgeBaseId,
                                                                   @NotNull(message = "页码不能为空") Integer page,
                                                                   @NotNull(message = "页面大小不能为空") Integer pageSize) {
        NoteTaskQueryParam noteTaskQueryParam = new NoteTaskQueryParam();
        noteTaskQueryParam.setId(knowledgeBaseId);
        noteTaskQueryParam.setPage(page);
        noteTaskQueryParam.setPageSize(pageSize);
        return ResUtil.success(noteTaskService.getMemberNoteTasks(noteTaskQueryParam));
    }






}
