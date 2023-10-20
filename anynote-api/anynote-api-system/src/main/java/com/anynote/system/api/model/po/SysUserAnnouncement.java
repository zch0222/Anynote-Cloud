package com.anynote.system.api.model.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 称霸幼儿园
 */
@TableName("sys_user_announcement")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserAnnouncement {
    /**
     * 主键ID，自增
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 公告ID
     */
    private Long announcementId;

    /**
     * 阅读时间，默认为当前时间
     */
    private Date readTime;
}
