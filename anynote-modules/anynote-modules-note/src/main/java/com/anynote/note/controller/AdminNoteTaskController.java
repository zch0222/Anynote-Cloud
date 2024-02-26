package com.anynote.note.controller;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.CreateResEntity;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.api.model.bo.NoteOperationCount;
import com.anynote.note.api.model.po.NoteTaskSubmissionRecord;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.dto.*;
import com.anynote.note.model.vo.NoteTaskUserAnalyzeVO;
import com.anynote.note.service.NoteTaskService;
import com.anynote.note.service.NoteTaskSubmissionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @GetMapping("{id}")
    public ResData<AdminNoteTaskDTO> getAdminNoteTaskById(@PathVariable @NotNull(message = "任务id不能为空") Long id) {
        return ResUtil.success(noteTaskService.getAdminNoteTaskById(NoteTaskQueryParam.NoteTaskQueryParamBuilder()
                .noteTaskId(id)
                .knowledgeBaseId(noteTaskService.getNoteTaskKnowledgeBaseId(id))
                .build()));
    }

    @PatchMapping("{id}")
    public ResData<String> updateNoteTask(@RequestBody NoteTaskUpdateDTO noteTaskUpdateDTO,
                                          @PathVariable("id") Long id) {
        noteTaskUpdateDTO.setId(id);
        return ResUtil.success(noteTaskService.updateNoteTask(NoteTaskUpdateParam.NoteTaskUpdateParamBuilder()
                        .taskName(noteTaskUpdateDTO.getTaskName())
                        .endTime(noteTaskUpdateDTO.getEndTime())
                        .startTime(noteTaskUpdateDTO.getStartTime())
                        .noteTaskId(noteTaskUpdateDTO.getId())
                        .taskDescribe(noteTaskUpdateDTO.getTaskDescribe())
                .build()));

    }

    @GetMapping("submissions")
    public ResData<PageBean<NoteTaskSubmissionRecordDTO>> getAdminNoteTaskSubmissionRecords(@NotNull(message = "任务id不能为空") Long noteTaskId,
                                                                                            @NotNull(message = "页码不能未空") Integer page,
                                                                                            @NotNull(message = "页码容量不能为空") Integer pageSize) {
        NoteTaskSubmissionRecordQueryParam noteTaskSubmissionRecordQueryParam = new NoteTaskSubmissionRecordQueryParam();
        noteTaskSubmissionRecordQueryParam.setNoteTaskId(noteTaskId);
        noteTaskSubmissionRecordQueryParam.setPageSize(pageSize);
        noteTaskSubmissionRecordQueryParam.setPage(page);
        return ResUtil.success(noteTaskSubmissionRecordService.getNoteTaskSubmitRecords(noteTaskSubmissionRecordQueryParam));
    }


    @GetMapping("/{id}/operationCounts")
    public ResData<List<NoteOperationCount>> getNoteOperationCounts(@NotNull(message = "任务id不能为空") @PathVariable("id") Long id) {
        return ResUtil.success(noteTaskService.getNoteOperationCounts(NoteTaskQueryParam.NoteTaskQueryParamBuilder()
                        .noteTaskId(id)
                .build()));
    }

    /**
     * 退回提交记录
     * @param id
     * @return
     */
    @PostMapping("submissions/return/{id}")
    public ResData<String> returnSubmissions(@NotNull(message = "提交记录id不能为空") @PathVariable("id") Long id) {
        NoteTaskSubmissionRecord noteTaskSubmissionRecord = noteTaskSubmissionRecordService.getBaseMapper().selectById(id);
        return ResUtil.success(noteTaskService.returnSubmission(new SubmissionReturnParam(noteTaskSubmissionRecord, noteTaskSubmissionRecord.getNoteTaskId())));
    }

    /**
     * 获取用户的任务分析
     * @return
     */
    @GetMapping("analyze/user")
    public ResData<NoteTaskUserAnalyzeVO> getUserNoteTaskAnalyze(@Validated UserNoteTaskAnalyzeDTO userNoteTaskAnalyzeDTO) {
        return ResUtil.success(noteTaskService.getUserNoteTaskAnalyze(NoteTaskAnalyzeQueryParam.NoteTaskAnalyzeQueryParamBuilder()
                .userId(userNoteTaskAnalyzeDTO.getUserId())
                .knowledgeBaseId(userNoteTaskAnalyzeDTO.getKnowledgeBaseId())
                .page(userNoteTaskAnalyzeDTO.getPage())
                .pageSize(userNoteTaskAnalyzeDTO.getPageSize()).build()));
    }




}
