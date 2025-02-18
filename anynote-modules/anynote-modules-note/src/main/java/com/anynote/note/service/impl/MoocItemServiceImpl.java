package com.anynote.note.service.impl;

import com.anynote.note.mapper.MoocItemMapper;
import com.anynote.note.model.po.MoocItemPO;
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
}