package com.anynote.note.controller;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.CreateResEntity;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.model.bo.NoteTaskCreateParam;
import com.anynote.note.model.bo.NoteTaskSubmitParam;
import com.anynote.note.model.dto.NoteTaskSubmissionRecordCreateDTO;
import com.anynote.note.model.dto.NoteTaskCreateDTO;
import com.anynote.note.service.NoteTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @PostMapping
    public ResData<CreateResEntity> createNoteTask(@RequestBody @Valid NoteTaskCreateDTO noteTaskCreateDTO) {
        Long noteTaskId = noteTaskService.createNoteTask(new NoteTaskCreateParam(noteTaskCreateDTO));
        return ResUtil.success(CreateResEntity.builder()
                .id(noteTaskId)
                .build());
    }

    @PostMapping("submit")
    public ResData<String> submitNoteTask(@RequestBody @Validated NoteTaskSubmissionRecordCreateDTO noteTaskSubmissionRecordCreateDTO) {
        return ResUtil.success(noteTaskService
                .submitNoteTask(new NoteTaskSubmitParam(noteTaskSubmissionRecordCreateDTO)));
    }
}
