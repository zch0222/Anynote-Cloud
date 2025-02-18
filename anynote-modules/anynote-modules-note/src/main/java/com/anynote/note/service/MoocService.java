package com.anynote.note.service;

import com.anynote.core.web.model.bo.PageBean;
import com.anynote.note.model.bo.MoocCreateParam;
import com.anynote.note.model.bo.MoocQueryParam;
import com.anynote.note.model.dto.MoocListDTO;
import com.anynote.note.model.po.MoocPO;
import com.anynote.note.model.vo.MoocListVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 慕课服务
 * @author 称霸幼儿园
 */
public interface MoocService extends IService<MoocPO> {

    /**
     * 创建慕课
     * @param moocCreateParam 慕课创建参数
     * @return 慕课id
     */
    public Long createMooc(MoocCreateParam moocCreateParam);

    /**
     * 分页获取慕课列表
     * @param moocQueryParam
     * @return
     */
    public PageBean<MoocListVO> getMoocList(MoocQueryParam moocQueryParam);
}