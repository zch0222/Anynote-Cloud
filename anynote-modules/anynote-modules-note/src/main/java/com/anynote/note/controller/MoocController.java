package com.anynote.note.controller;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.model.dto.OssSliceUploadTaskCreatePublicDTO;
import com.anynote.file.api.model.vo.OssSliceUploadTaskVO;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.dto.*;
import com.anynote.note.model.vo.MoocItemListVO;
import com.anynote.note.model.vo.MoocItemVO;
import com.anynote.note.model.vo.MoocListVO;
import com.anynote.note.service.MoocItemService;
import com.anynote.note.service.MoocService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 慕课 Controller
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("/moocs")
public class MoocController {


    @Resource
    private MoocService moocService;

    @Resource
    private MoocItemService moocItemService;

    @PostMapping("")
    public ResData<Long> createMooc(@Validated @RequestBody MoocCreateDTO moocCreateDTO) {
        return ResUtil.success(moocService.createMooc(MoocCreateParam.MoocCreateParamBuilder()
                .knowledgeBaseId(moocCreateDTO.getKnowledgeBaseId())
                .moocDescription(moocCreateDTO.getMoocDescription())
                .title(moocCreateDTO.getTitle())
                .dataScope(moocCreateDTO.getDataScope())
                .cover(moocCreateDTO.getCover())
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

    /**
     * 创建封面上传任务
     * @param ossSliceUploadTaskCreatePublicDTO
     * @return
     */
    @PostMapping("cover/create")
    public ResData<OssSliceUploadTaskVO> createMoocCoverUploadTask(@Validated @RequestBody
                                                                       OssSliceUploadTaskCreatePublicDTO ossSliceUploadTaskCreatePublicDTO) {
        return ResUtil.success(moocService.createMoocCoverUploadTask(ossSliceUploadTaskCreatePublicDTO));
    }

    /**
     * 创建moocItems
     * @param createDTO
     * @return
     */
    @PostMapping("items")
    public ResData<String> createMoocItems(@Validated @RequestBody MoocItemCreateDTO createDTO) {
        return ResUtil.success(moocService.createItems(MoocItemCreateParam.MoocItemCreateParamBuilder()
                .moocId(createDTO.getMoocId())
                .knowledgeBaseId(createDTO.getKnowledgeBaseId())
                .items(createDTO.getItems())
                .build()));
    }

    /**
     * 获取慕课Item列表
     * @param moocItemListDTO
     * @return
     */
    @GetMapping("items")
    public ResData<PageBean<MoocItemListVO>> getMoocItemList(@Validated MoocItemListDTO moocItemListDTO) {
        return ResUtil.success(moocService.getMoocItemList(MoocItemQueryParam.MoocItemQueryParamBuilder()
                .moocId(moocItemListDTO.getMoocId())
                .parentId(moocItemListDTO.getParentId())
                .moocItemType(moocItemListDTO.getMoocItemType())
                .page(moocItemListDTO.getPage())
                .pageSize(moocItemListDTO.getPageSize())
                .build()));
    }

    /**
     * 根据慕课Item ID获取慕课Item信息
     * @param moocItemId
     * @param moocId
     * @return
     */
    @GetMapping("items/{moocItemId}")
    public ResData<MoocItemVO> getMoocItem(@PathVariable Long moocItemId,
                                           @Validated @NotNull(message = "慕课id不能为空") Long moocId) {
        return ResUtil.success(moocItemService.getMoocItemVOById(MoocItemQueryParam.MoocItemQueryParamBuilder()
                .moocId(moocId)
                .moocItemId(moocItemId)
                .build()));
    }

    /**
     * 创建慕课视频上传任务
     * @param createDTO
     * @return OssSliceUploadTaskVO
     */
    @PostMapping("video/create")
    public ResData<OssSliceUploadTaskVO> createMoocVideoUploadTask(@Validated @RequestBody
                                                                   MoocVideoUploadTaskCreateDTO createDTO) {
        return ResUtil.success(moocService.createMoocVideoUploadTask(MoocVideoCreateParam.MoocVideoCreateParamBuilder()
                        .ossSliceUploadTaskCreatePublicDTO(createDTO)
                        .moocId(createDTO.getMoocId())
                .build()));
    }

    /**
     * 慕课Item 语音识别
     * @param moocAsrDTO
     * @return SUCCESS
     */
    @PostMapping("asr")
    public ResData<String> asrMoocItem(@RequestBody @Validated MoocAsrDTO moocAsrDTO) {
        return ResUtil.success(moocService.moocItemAsr(MoocItemAsrParam.MoocItemAsrParamBuilder()
                .moocId(moocAsrDTO.getMoocId())
                .moocItemId(moocAsrDTO.getMoocItemId())
                .language(moocAsrDTO.getLanguage())
                .build()));
    }

//    @GetMapping("{id}")
//    public ResData<>

}
