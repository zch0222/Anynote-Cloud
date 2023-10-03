package com.anynote.note.controller;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.CreateResEntity;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.model.bo.NoteTaskCreateParam;
import com.anynote.note.model.bo.NoteTaskQueryParam;
import com.anynote.note.model.dto.AdminNoteTaskDTO;
import com.anynote.note.model.dto.NoteTaskCreateDTO;
import com.anynote.note.model.dto.NoteTaskSubmissionRecordDTO;
import com.anynote.note.service.NoteTaskService;
import com.anynote.note.service.NoteTaskSubmissionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 知识库管理员任务接口
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("/admin/noteTasks")
@Validated
public class AdminNoteTaskController {
    @Autowired
    private NoteTaskService noteTaskService;

    @Autowired
    private NoteTaskSubmissionRecordService noteTaskSubmissionRecordService;

    @PostMapping
    public ResData<CreateResEntity> createNoteTask(@RequestBody @Valid NoteTaskCreateDTO noteTaskCreateDTO) {
        Long noteTaskId = noteTaskService.createNoteTask(new NoteTaskCreateParam(noteTaskCreateDTO));
        return ResUtil.success(CreateResEntity.builder()
                .id(noteTaskId)
                .build());
    }

    @GetMapping
    public ResData<PageBean<AdminNoteTaskDTO>> getAdminNoteTaskList(@NotNull(message = "页码不能为空") Integer page,
                                                                    @NotNull(message = "页面大小不能为空") Integer pageSize,
                                                                    @NotNull(message = "知识库id不能为空") Long knowledgeBaseId) {
        NoteTaskQueryParam noteTaskQueryParam = new NoteTaskQueryParam();
        noteTaskQueryParam.setId(knowledgeBaseId);
        noteTaskQueryParam.setPage(page);
        noteTaskQueryParam.setPageSize(pageSize);
        return ResUtil.success(noteTaskService.getAdminNoteTasks(noteTaskQueryParam));
    }


    @GetMapping("submissions")
    public ResData<PageBean<NoteTaskSubmissionRecordDTO>> getAdminNoteTaskSubmissionRecords(@NotNull(message = "任务id不能为空") Long noteTaskId,
                                                                                            @NotNull(message = "页码不能未空") Integer page,
                                                                                            @NotNull(message = "页码容量不能为空") Integer pageSize) {
        return ResUtil.success(noteTaskSubmissionRecordService.getNoteTaskSubmitRecords(noteTaskId, page, pageSize));
    }




}