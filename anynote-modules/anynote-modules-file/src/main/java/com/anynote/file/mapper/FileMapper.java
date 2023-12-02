package com.anynote.file.mapper;

import com.anynote.file.api.model.po.FilePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件Mapper
 * @author 称霸幼儿园
 */
@Mapper
public interface FileMapper extends BaseMapper<FilePO> {
}
