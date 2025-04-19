package com.anynote.notify.mapper;

import com.anynote.notify.api.model.po.Notice;
import com.anynote.notify.model.vo.NoticeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 称霸幼儿园
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    public List<NoticeVO> selectNoticeList(@Param("userId") Long userId);
}
