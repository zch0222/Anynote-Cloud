package com.anynote.system.model.vo;

import com.anynote.core.serialization.CustomNullSerializer;
import com.anynote.core.serialization.EmptyToBracesSerializer;
import com.anynote.core.serialization.NullObjectJsonSerializer;
import com.anynote.system.api.model.po.SysAnnouncement;
import com.anynote.system.api.model.po.SysUser;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AnnouncementVO {

    /**
     * 1.存在未读消息 0.不存在未读消息
     */
    private Integer hasUnReadAnnouncement;

    @JsonSerialize(nullsUsing = NullObjectJsonSerializer.class)
    private SysAnnouncement announcement;
}
