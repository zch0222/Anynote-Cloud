package com.anynote.note.service.impl;

import com.anynote.common.datascope.annotation.RequiresPermissions;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.StringUtils;
import com.anynote.note.mapper.MoocItemMapper;
import com.anynote.note.model.bo.MoocItemQueryParam;
import com.anynote.note.model.po.MoocItemPO;
import com.anynote.note.model.vo.MoocItemVO;
import com.anynote.note.service.MoocItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Mooc Item服务实现类
 * @author 称霸幼儿园
 */
@Service
public class MoocItemServiceImpl extends ServiceImpl<MoocItemMapper, MoocItemPO>
        implements MoocItemService {


    @RequiresPermissions(value = "n:mooc:read", paramIdName = "moocId", queryParamName = "moocItemQueryParam")
    @Override
    public MoocItemVO getMoocItemVOById(MoocItemQueryParam moocItemQueryParam) {
        MoocItemVO moocItemVO = this.baseMapper
                .selectMoocItemVOById(moocItemQueryParam.getMoocItemId(), moocItemQueryParam.getMoocId());
        if (StringUtils.isNull(moocItemVO)) {
            throw new BusinessException("慕课对象不存在");
        }
        return moocItemVO;
    }
}