package com.anynote.note.controller;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.note.model.bo.MoocCreateParam;
import com.anynote.note.model.bo.MoocQueryParam;
import com.anynote.note.model.dto.MoocCreateDTO;
import com.anynote.note.model.dto.MoocListDTO;
import com.anynote.note.model.vo.MoocListVO;
import com.anynote.note.service.MoocService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 慕课 Controller
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("/moocs")
public class MoocController {


    @Resource
    private MoocService moocService;

    @PostMapping("")
    public ResData<Long> createMooc(@Validated @RequestBody MoocCreateDTO moocCreateDTO) {
        return ResUtil.success(moocService.createMooc(MoocCreateParam.MoocCreateParamBuilder()
                .knowledgeBaseId(moocCreateDTO.getKnowledgeBaseId())
                .moocDescription(moocCreateDTO.getMoocDescription())
                .title(moocCreateDTO.getTitle())
                .dataScope(moocCreateDTO.getDataScope())
                .build()));
    }

    @GetMapping("")
    public ResData<PageBean<MoocListVO>> getMoocList(@Validated MoocListDTO moocListDTO) {
        return ResUtil.success(moocService.getMoocList(MoocQueryParam
                .MoocQueryParamBuilder()
                .knowledgeBaseId(moocListDTO.getKnowledgeId())
                .page(moocListDTO.getPage())
                .pageSize(moocListDTO.getPageSize())
                .build()));
    }


    @GetMapping("{id}")
    public ResData<>

}
