package com.anynote.note.mapper;

import com.anynote.note.model.bo.MoocItemQueryParam;
import com.anynote.note.model.po.MoocItemPO;
import com.anynote.note.model.vo.MoocItemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mooc Item Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface MoocItemMapper extends BaseMapper<MoocItemPO> {

    /**
     * 获取MoocItemVO
     * @param moocItemId moocItem id
     * @return MoocItemVO
     */
    public MoocItemVO selectMoocItemVOById(@Param("moocItemId") Long moocItemId, @Param("moocId") Long moocId);
}
