package com.anynote.note.model.vo;

import com.anynote.core.serialization.NullNumberJsonSerializer;
import com.anynote.core.serialization.NullStringJsonSerializer;
import com.anynote.core.web.model.bo.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteTaskHistoryVO extends BaseEntity {

    /**
     * id
     */
    private Long id;

    /**
     * 笔记任务id
     */
    private Long noteTaskId;

    /**
     * 操作类型 1.创建 2.修改任务 3.提交任务 4.退回提交 5.添加用户
     */
    private Integer type;

    /**
     * 操作者id
     */
    private Long operatorId;

    /**
     * 操作者用户名
     */
    private String operatorNickName;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 受影响的任务管理用户
     */
    private Long noteTaskUserId;

    /**
     * 笔记提交记录id
     */
    private Long noteTaskSubmissionRecordId;

    /**
     * 相关的笔记id
     */
    @JsonSerialize(nullsUsing = NullNumberJsonSerializer.class)
    private Long noteId;

    /**
     * 笔记历史id
     */
    @JsonSerialize(nullsUsing = NullNumberJsonSerializer.class)
    private Long noteHistoryId;

    /**
     * 笔记历史标题
     */
    @JsonSerialize(nullsUsing = NullStringJsonSerializer.class)
    private String noteHistoryTitle;

}
