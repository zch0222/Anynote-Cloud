package com.anynote.system.service;

import com.anynote.system.api.model.po.SysAnnouncement;
import com.anynote.system.model.vo.AnnouncementVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 称霸幼儿园
 */
public interface SysAnnouncementService extends IService<SysAnnouncement> {

    public AnnouncementVO getLatestAnnouncement();

    public Long readAnnouncement(Long announcementId);

    public SysAnnouncement selectAnnouncementById(Long id);
}
