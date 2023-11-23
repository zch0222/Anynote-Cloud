package com.anynote.note.service.impl;

import com.anynote.note.api.model.po.Doc;
import com.anynote.note.mapper.DocMapper;
import com.anynote.note.service.DocService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 文档服务器 IMPL
 * @author 称霸幼儿园
 */
@Service
public class DocServiceImpl extends ServiceImpl<DocMapper, Doc>
        implements DocService {


}
