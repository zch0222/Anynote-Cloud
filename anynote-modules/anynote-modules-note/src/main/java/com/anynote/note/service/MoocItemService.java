package com.anynote.note.service;

import com.anynote.note.model.bo.MoocItemQueryParam;
import com.anynote.note.model.po.MoocItemPO;
import com.anynote.note.model.vo.MoocItemVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Mooc Item 服务
 * @author 称霸幼儿园
 */
public interface MoocItemService extends IService<MoocItemPO> {

    /**
     * 获取MoocItemVO
     * @param moocItemQueryParam 慕课Item 查询参数
     * @return MoocItemVO
     */
    public MoocItemVO getMoocItemVOById(MoocItemQueryParam moocItemQueryParam);
}
