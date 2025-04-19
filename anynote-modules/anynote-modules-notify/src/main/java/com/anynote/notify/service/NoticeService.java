package com.anynote.notify.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.notify.api.model.bo.NoticePublishParam;
import com.anynote.notify.api.model.po.Notice;
import com.anynote.notify.model.dto.NoticeListDTO;
import com.anynote.notify.model.vo.NoticeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.weaver.ast.Not;
import reactor.core.publisher.Mono;

public interface NoticeService extends IService<Notice> {

    public Long publishNotice(NoticePublishParam noticePublishParam);

    public int saveNotice(Notice notice);

    public Mono<PageBean<NoticeVO>> getNoticeList(NoticeListDTO noticeListDTO);

}
