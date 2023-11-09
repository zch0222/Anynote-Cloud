package com.anynote.note.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@Data
public class NoteHistoryListItemVO {

    /**
     * 操作日志id
     */
    private Long operationLogId;

    /**
     * 更新时间
     */
    private Date operationTime;

    /**
     * 更新者id
     */
    private Long updaterId;

    /**
     * 更新者的昵称
     */
    private String updaterNickname;

    /**
     * 更新者用户名
     */
    private String updaterUsername;
}
