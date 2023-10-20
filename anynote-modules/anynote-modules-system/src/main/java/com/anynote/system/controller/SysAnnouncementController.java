package com.anynote.system.controller;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.CreateResEntity;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.system.api.model.po.SysAnnouncement;
import com.anynote.system.model.dto.AnnouncementReadDTO;
import com.anynote.system.model.vo.AnnouncementVO;
import com.anynote.system.service.SysAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 称霸幼儿园
 */
@RequestMapping("announcements")
@RestController
public class SysAnnouncementController {

    @Autowired
    private SysAnnouncementService sysAnnouncementService;

    @GetMapping("latest")
    public ResData<AnnouncementVO> getLatestAnnouncement() {
        return ResUtil.success(sysAnnouncementService.getLatestAnnouncement());
    }


    @PostMapping("read")
    public ResData<CreateResEntity> readAnnouncement(@RequestBody AnnouncementReadDTO announcementReadDTO) {
        return ResUtil.success(CreateResEntity.builder()
                .id(sysAnnouncementService.readAnnouncement(announcementReadDTO.getAnnouncementId()))
                .build());
    }

}
