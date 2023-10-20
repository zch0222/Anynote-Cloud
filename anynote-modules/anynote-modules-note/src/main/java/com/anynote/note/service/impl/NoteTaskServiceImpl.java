package com.anynote.note.service.impl;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.constant.Constants;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.exception.user.UserParamException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.api.model.po.Note;
import com.anynote.note.api.model.po.NoteTask;
import com.anynote.note.api.model.po.NoteTaskSubmissionRecord;
import com.anynote.note.api.model.po.UserNoteTask;
import com.anynote.note.datascope.annotation.RequiresKnowledgeBasePermissions;
import com.anynote.note.datascope.annotation.RequiresNotePermissions;
import com.anynote.note.enums.KnowledgeBasePermissions;
import com.anynote.note.enums.NotePermissions;
import com.anynote.note.enums.NoteTaskPermissions;
import com.anynote.note.mapper.NoteTaskMapper;
import com.anynote.note.mapper.NoteTaskSubmissionRecordMapper;
import com.anynote.note.mapper.UserNoteTaskMapper;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.dto.AdminNoteTaskDTO;
import com.anynote.note.model.dto.MemberNoteTaskDTO;
import com.anynote.note.model.dto.NoteTaskSubmissionRecordDTO;
import com.anynote.note.service.KnowledgeBaseService;
import com.anynote.note.service.NoteService;
import com.anynote.note.service.NoteTaskService;
import com.anynote.note.service.NoteTaskSubmissionRecordService;
import com.anynote.note.validate.annotation.PageValid;
import com.anynote.system.api.model.bo.LoginUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 称霸幼儿园
 */
@Service
@Slf4j
public class NoteTaskServiceImpl extends ServiceImpl<NoteTaskMapper, NoteTask>
        implements NoteTaskService {

    @Autowired
    private NoteTaskSubmissionRecordService noteTaskSubmissionRecordService;

    @Autowired
    private UserNoteTaskMapper userNoteTaskMapper;

    @Autowired
    private NoteService noteService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor;

    @Autowired
    private NoteTaskMapper noteTaskMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.MANAGE,
            message = "没有权限创建任务")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createNoteTask(NoteTaskCreateParam taskCreateParam) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        Date date = new Date();
        NoteTask noteTask = NoteTask.builder()
                .taskName(taskCreateParam.getTaskName())
                .startTime(taskCreateParam.getStartTime())
                .endTime(taskCreateParam.getEndTime())
                .knowledgeBaseId(taskCreateParam.getId())
                .status(0)
                .deleted(0)
                .build();
        noteTask.setCreateBy(loginUser.getSysUser().getId());
        noteTask.setUpdateBy(loginUser.getSysUser().getId());
        noteTask.setCreateTime(date);
        noteTask.setUpdateTime(date);
        this.baseMapper.insert(noteTask);
        // 异步执行
        asyncExecutor.submit(() -> {
            // 获取知识库内所有非管理员用户的id
            List<Long> knowledgeBaseMemberIds = knowledgeBaseService.getAllMemberKnowledgeBaseUserId(taskCreateParam.getId());
            // 给非管理员用户添加编辑权限
            for (Long userId : knowledgeBaseMemberIds) {
                userNoteTaskMapper.insert(UserNoteTask.builder()
                        .userId(userId)
                        .noteTaskId(noteTask.getId())
                        .permissions(NoteTaskPermissions.SUBMIT.getValue())
                        // 管理员不用提交任务
                        .status(2)
                        .build());
            }

            List<Long> knowledgeBaseManagerIds = knowledgeBaseService.getAllKnowledgeBaseManagerId(taskCreateParam.getId());
            // 给管理员用户添加管理权限
            for (Long userId : knowledgeBaseManagerIds) {
                userNoteTaskMapper.insert(UserNoteTask.builder()
                        .userId(userId)
                        .noteTaskId(noteTask.getId())
                        .permissions(NoteTaskPermissions.MANAGE.getValue())
                        .status(0)
                        .build());
            }
        });
        return noteTask.getId();
    }

    @Override
    public Long getNoteTaskNeedSubmitCount(NoteTaskQueryParam queryParam) {
        LambdaQueryWrapper<UserNoteTask> userNoteTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userNoteTaskLambdaQueryWrapper
                .eq(UserNoteTask::getNoteTaskId, queryParam.getNoteTaskId())
                .gt(UserNoteTask::getPermissions, NoteTaskPermissions.MANAGE.getValue());
        return userNoteTaskMapper.selectCount(userNoteTaskLambdaQueryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.EDIT,
            message = "没有权限提交任务")
    @RequiresNotePermissions(NotePermissions.MANAGE)
    @Override
    public String submitNoteTask(NoteTaskSubmitParam submitParam) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        Date date = new Date();

        LambdaQueryWrapper<NoteTask> noteTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noteTaskLambdaQueryWrapper
                .eq(NoteTask::getId, submitParam.getTaskId())
                .select(NoteTask::getStartTime, NoteTask::getEndTime, NoteTask::getStatus, NoteTask::getKnowledgeBaseId);
        NoteTask noteTaskInfo = this.baseMapper.selectOne(noteTaskLambdaQueryWrapper);

        LambdaQueryWrapper<Note> noteLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noteLambdaQueryWrapper
                .eq(Note::getId, submitParam.getId())
                .select(Note::getKnowledgeBaseId);
        Note noteInfo = noteService.getBaseMapper().selectOne(noteLambdaQueryWrapper);

        if (!noteInfo.getKnowledgeBaseId().equals(noteTaskInfo.getKnowledgeBaseId())) {
            throw new UserParamException("提交失败，笔记和任务不属于一个知识库", ResCode.USER_REQUEST_PARAM_ERROR);
        }

        if (StringUtils.isNull(noteTaskInfo)) {
            throw new UserParamException("提交失败，任务不存在", ResCode.USER_REQUEST_PARAM_ERROR);
        }
        if (date.getTime() > noteTaskInfo.getEndTime().getTime()) {
            // 创建一个异步任务
            if (0 == noteTaskInfo.getStatus()) {

            }
            throw new UserParamException("提交失败，任务已经结束", ResCode.USER_REQUEST_PARAM_ERROR);
        }
        else if (date.getTime() < noteTaskInfo.getStartTime().getTime()) {
            throw new UserParamException("提交失败，任务尚未开始", ResCode.USER_REQUEST_PARAM_ERROR);
        }

        LambdaQueryWrapper<NoteTaskSubmissionRecord> noteTaskSubmissionRecordLambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        noteTaskSubmissionRecordLambdaQueryWrapper
                .eq(NoteTaskSubmissionRecord::getUserId, loginUser.getSysUser().getId())
                .eq(NoteTaskSubmissionRecord::getNoteTaskId, submitParam.getTaskId());

        Long recordCount = noteTaskSubmissionRecordService.getBaseMapper()
                .selectCount(noteTaskSubmissionRecordLambdaQueryWrapper);
        if (recordCount > 0) {
            throw new UserParamException("提交失败，你已经提交过该任务", ResCode.USER_REQUEST_PARAM_ERROR);
        }


        NoteTaskSubmissionRecord noteTaskSubmissionRecord = NoteTaskSubmissionRecord.builder()
                .noteTaskId(submitParam.getTaskId())
                .userId(loginUser.getSysUser().getId())
                .noteId(submitParam.getId())
                .submitTime(new Date())
                .deleted(0)
                .build();
        noteTaskSubmissionRecord.setUpdateBy(loginUser.getSysUser().getId());
        noteTaskSubmissionRecord.setCreateBy(loginUser.getSysUser().getId());
        noteTaskSubmissionRecord.setUpdateTime(date);
        noteTaskSubmissionRecord.setCreateTime(date);
        noteTaskSubmissionRecordService.getBaseMapper().insert(noteTaskSubmissionRecord);
//        rocketMQTemplate.asyncSendOrderly("note_task_topic", MessageBuilder
//                .withPayload("测试").build(), "id", new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
//                    log.info("发送异步顺序消息成功!消息ID为:{}", sendResult.getMsgId());
//                }
//            }
//            @Override
//            public void onException(Throwable throwable) {
//                log.info("发送异步顺序消息失败!失败原因为:{}", throwable.getMessage());
//            }
//        });
        Integer count = noteService.submitNote(submitParam.getId());
        if (1 != count) {
            throw new BusinessException("提交失败，请联系管理员", ResCode.BUSINESS_ERROR);
        }
        return Constants.SUCCESS_RES;
    }

    @PageValid
    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.READ, message = "无法获取笔记任务信息")
    @Override
    public PageBean<MemberNoteTaskDTO> getMemberNoteTasks(NoteTaskQueryParam queryParam) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        queryParam.setSubmitUserId(loginUser.getSysUser().getId());
        PageHelper.startPage(queryParam.getPage(), queryParam.getPageSize(), "update_time desc");
        List<MemberNoteTaskDTO> memberNoteTaskDTOList = this.baseMapper.selectMemberNoteTaskList(queryParam);
        PageInfo<MemberNoteTaskDTO> pageInfo = new PageInfo<>(memberNoteTaskDTOList);


        memberNoteTaskDTOList.stream().forEach(memberNoteTaskDTO -> {
            if (StringUtils.isNotNull(memberNoteTaskDTO.getSubmissionNoteId())) {
                memberNoteTaskDTO.setSubmissionStatus(0);
            }
            else {
                memberNoteTaskDTO.setSubmissionStatus(1);
            }
        });
        return PageBean.<MemberNoteTaskDTO>builder()
                .rows(memberNoteTaskDTOList)
                .pages(pageInfo.getPages())
                .total(pageInfo.getTotal())
                .build();
    }



//    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.MANAGE, message = "权限不足")
//    @Override
//    public AdminNoteTaskDTO getAdminNoteTaskById(NoteTaskQueryParam queryParam) {
//        queryParam.setId(this.getNoteTaskKnowledgeBaseId(queryParam.getNoteTaskId()));
//        AdminNoteTaskDTO adminNoteTaskDTO =  selectAdminNoteTaskById(queryParam);
//        return adminNoteTaskDTO;
//    }


    @Override
    public Long getNoteTaskKnowledgeBaseId(Long noteTaskId) {
        LambdaQueryWrapper<NoteTask> noteTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noteTaskLambdaQueryWrapper
                .eq(NoteTask::getId, noteTaskId)
                .select(NoteTask::getKnowledgeBaseId);
        NoteTask noteTask = this.baseMapper.selectOne(noteTaskLambdaQueryWrapper);
        return noteTask.getKnowledgeBaseId();
    }

    @Override
    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.MANAGE, message = "权限不足")
    public AdminNoteTaskDTO getAdminNoteTaskById(NoteTaskQueryParam queryParam) {
        LambdaQueryWrapper<NoteTask> noteTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noteTaskLambdaQueryWrapper
                .eq(NoteTask::getId, queryParam.getNoteTaskId());
        NoteTask noteTask = this.baseMapper.selectOne(noteTaskLambdaQueryWrapper);
        return this.getAdminNoteTaskInfo(noteTask);
    }

    private AdminNoteTaskDTO getAdminNoteTaskInfo(NoteTask noteTask) {
        Long needSubmitCount = this.getNoteTaskNeedSubmitCount(NoteTaskQueryParam.NoteTaskQueryParamBuilder()
                .noteTaskId(noteTask.getId())
                .build());
        LambdaQueryWrapper<NoteTaskSubmissionRecord> submissionRecordLambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        submissionRecordLambdaQueryWrapper
                .eq(NoteTaskSubmissionRecord::getNoteTaskId, noteTask.getId());
        noteTask.setSubmittedCount(noteTaskSubmissionRecordService
                .getBaseMapper().selectCount(submissionRecordLambdaQueryWrapper));
        AdminNoteTaskDTO adminNoteTaskDTO = new AdminNoteTaskDTO(noteTask);
        adminNoteTaskDTO.setNeedSubmitCount(needSubmitCount);
        if (0L == needSubmitCount) {
            adminNoteTaskDTO.setSubmissionProgress(100.0);
        }
        else {
            adminNoteTaskDTO.setSubmissionProgress(new BigDecimal(1.0 * adminNoteTaskDTO.getSubmittedCount() / needSubmitCount)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        return adminNoteTaskDTO;
    }

    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.MANAGE,
            message = "没有权限查看笔记任务信息")
    @PageValid
    @Override
    public PageBean<AdminNoteTaskDTO> getAdminNoteTasks(NoteTaskQueryParam queryParam) {

        LambdaQueryWrapper<NoteTask> noteTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noteTaskLambdaQueryWrapper
                .eq(NoteTask::getKnowledgeBaseId, queryParam.getId());
        PageHelper.startPage(queryParam.getPage(), queryParam.getPageSize(), "create_time desc");
        List<NoteTask> noteTaskList = this.baseMapper.selectList(noteTaskLambdaQueryWrapper);
        PageInfo<NoteTask> pageInfo = new PageInfo<>(noteTaskList);

//        Long needSubmitCount = knowledgeBaseService.getKnowledgeBaseMemberCount(queryParam);
//        DecimalFormat df = new DecimalFormat("#.00");

        List<AdminNoteTaskDTO> adminNoteTaskDTOList = noteTaskList.stream()
                .map(noteTask -> {
                    Long needSubmitCount = this.getNoteTaskNeedSubmitCount(NoteTaskQueryParam.NoteTaskQueryParamBuilder()
                            .noteTaskId(noteTask.getId())
                            .build());
                    LambdaQueryWrapper<NoteTaskSubmissionRecord> submissionRecordLambdaQueryWrapper =
                            new LambdaQueryWrapper<>();
                    submissionRecordLambdaQueryWrapper
                            .eq(NoteTaskSubmissionRecord::getNoteTaskId, noteTask.getId());
                    noteTask.setSubmittedCount(noteTaskSubmissionRecordService
                            .getBaseMapper().selectCount(submissionRecordLambdaQueryWrapper));
                    AdminNoteTaskDTO adminNoteTaskDTO = new AdminNoteTaskDTO(noteTask);
                    adminNoteTaskDTO.setNeedSubmitCount(needSubmitCount);
                    if (0L == needSubmitCount) {
                        adminNoteTaskDTO.setSubmissionProgress(100.0);
                    }
                    else {
                        adminNoteTaskDTO.setSubmissionProgress(new BigDecimal(1.0 * adminNoteTaskDTO.getSubmittedCount() / needSubmitCount)
                                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    }

                    return adminNoteTaskDTO;
                })
                .collect(Collectors.toList());

//        PageBean<AdminNoteTaskDTO> pageBean = new PageBean<>();
//        pageBean.setPages(pageInfo.getPages());
//        pageBean.setTotal(pageInfo.getTotal());
//        pageBean.setRows(adminNoteTaskDTOList);
        return PageBean.<AdminNoteTaskDTO>builder()
                .rows(adminNoteTaskDTOList)
                .pages(pageInfo.getPages())
                .total(pageInfo.getTotal())
                .build();
    }
}
