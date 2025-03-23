package com.anynote.note.mapper;

import com.anynote.note.model.bo.MoocQueryParam;
import com.anynote.note.model.po.MoocPO;
import com.anynote.note.model.vo.MoocListVO;
import com.anynote.note.model.vo.MoocVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 慕课 Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface MoocMapper extends BaseMapper<MoocPO> {


    public List<MoocListVO> getMoocList(MoocQueryParam queryParam);

    public MoocVO selectMoocById(@Param("moocId") Long moocId);

}
