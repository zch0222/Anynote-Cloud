package com.anynote.system.service.impl;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.exception.user.UserParamException;
import com.anynote.core.utils.StringUtils;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.po.SysAnnouncement;
import com.anynote.system.api.model.po.SysUserAnnouncement;
import com.anynote.system.mapper.SysAnnouncementMapper;
import com.anynote.system.mapper.SysUserAnnouncementMapper;
import com.anynote.system.model.vo.AnnouncementVO;
import com.anynote.system.service.SysAnnouncementService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Service
public class SysAnnouncementServiceImpl extends ServiceImpl<SysAnnouncementMapper, SysAnnouncement>
        implements SysAnnouncementService {

    @Autowired
    private SysUserAnnouncementMapper sysUserAnnouncementMapper;

    @Autowired
    private TokenUtil tokenUtil;


    @Override
    public AnnouncementVO getLatestAnnouncement() {
        LoginUser loginUser = tokenUtil.getLoginUser();
        PageHelper.startPage(1, 1, "date_published desc");
        List<SysAnnouncement> sysAnnouncements = this.baseMapper.selectList(null);
        SysAnnouncement sysAnnouncement = sysAnnouncements.size() > 0 ? sysAnnouncements.get(0) : null;
        LambdaQueryWrapper<SysUserAnnouncement> sysUserAnnouncementLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserAnnouncementLambdaQueryWrapper
                .eq(SysUserAnnouncement::getAnnouncementId, StringUtils.isNotNull(sysAnnouncement) ? sysAnnouncement.getId() : -1)
                .eq(SysUserAnnouncement::getUserId, loginUser.getSysUser().getId());
        SysUserAnnouncement sysUserAnnouncement = this.sysUserAnnouncementMapper.selectOne(sysUserAnnouncementLambdaQueryWrapper);
        AnnouncementVO announcementVO = null;
        if (StringUtils.isNull(sysUserAnnouncement)) {
            announcementVO = AnnouncementVO.builder()
                    .hasUnReadAnnouncement(1)
                    .announcement(sysAnnouncement)
                    .build();
        }
        else {
            announcementVO = AnnouncementVO.builder()
                    .hasUnReadAnnouncement(0)
                    .build();
        }
        return announcementVO;
    }


    private SysUserAnnouncement selectSysUserAnnouncement(Long userId, Long announcementId) {
        LambdaQueryWrapper<SysUserAnnouncement> sysUserAnnouncementLambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        sysUserAnnouncementLambdaQueryWrapper
                .eq(SysUserAnnouncement::getUserId, userId)
                .eq(SysUserAnnouncement::getAnnouncementId, announcementId);
        return sysUserAnnouncementMapper.selectOne(sysUserAnnouncementLambdaQueryWrapper);
    }

    @Override
    public SysAnnouncement selectAnnouncementById(Long id) {
        LambdaQueryWrapper<SysAnnouncement> sysAnnouncementLambdaQueryWrapper =
                new LambdaQueryWrapper<>();
        sysAnnouncementLambdaQueryWrapper.eq(SysAnnouncement::getId, id);
        return this.baseMapper.selectOne(sysAnnouncementLambdaQueryWrapper);
    }

    @Override
    public Long readAnnouncement(Long announcementId) {
        LoginUser loginUser = tokenUtil.getLoginUser();

        SysAnnouncement sysAnnouncement = this.selectAnnouncementById(announcementId);
        if (StringUtils.isNull(sysAnnouncement)) {
            throw new UserParamException("公告不存在");
        }
        SysUserAnnouncement oldSysUserAnnouncement = this.selectSysUserAnnouncement(loginUser.getSysUser().getId(), announcementId);
        if (StringUtils.isNotNull(oldSysUserAnnouncement)) {
            return oldSysUserAnnouncement.getId();
        }
        SysUserAnnouncement sysUserAnnouncement = SysUserAnnouncement.builder()
                .userId(loginUser.getUserId())
                .announcementId(announcementId)
                .readTime(new Date())
                .build();
        sysUserAnnouncementMapper.insert(sysUserAnnouncement);
        return sysUserAnnouncement.getId();
    }
}
