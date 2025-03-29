package com.anynote.note.controller;

import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.model.dto.OssSliceUploadTaskCreatePublicDTO;
import com.anynote.file.api.model.vo.OssSliceUploadTaskVO;
import com.anynote.note.api.model.dto.MoocAsrInfoUpdateDTO;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.dto.*;
import com.anynote.note.model.vo.*;
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

    @PatchMapping("{id}")
    public ResData<String> updateMooc(@PathVariable("id") @Validated @NotNull(message = "慕课id不能为空") Long id,
                                      @Validated @RequestBody MoocUpdateDTO moocUpdateDTO) {
        return ResUtil.success(moocService.updateMooc(new MoocUpdateParam(id, moocUpdateDTO)));
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
     * 根据id获取慕课信息
     * @param id 慕课id
     * @return 慕课信息
     */
    @GetMapping("{id}")
    public ResData<MoocVO> getMoocById(@PathVariable("id") Long id) {
        return ResUtil.success(moocService.getMoocById(MoocQueryParam.MoocQueryParamBuilder()
                .moocId(id)
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
     * 更新慕课Item
     * @param moocItemUpdateDTO
     * @return SUCCESS
     */
    @PatchMapping("items/{itemId}")
    public ResData<String> updateMoocItems(@Validated @RequestBody MoocItemUpdateDTO moocItemUpdateDTO,
                                           @PathVariable @Validated @NotNull(message = "Item Id不能为空") Long itemId) {
        if (StringUtils.isNull(itemId)) {
            throw new BusinessException("Item Id不能为空");
        }
        return ResUtil.success(moocService.updateMoocItem(MoocItemUpdateParam.MoocItemUpdateParamBuilder()
                .moocId(moocItemUpdateDTO.getMoocId())
                .moocItemId(itemId)
                .title(moocItemUpdateDTO.getTitle())
                .objectName(moocItemUpdateDTO.getObjectName())
                .parentId(moocItemUpdateDTO.getParentId())
                .itemText(moocItemUpdateDTO.getItemText())
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
    public ResData<MoocItemAsrVO> asrMoocItem(@RequestBody @Validated MoocAsrDTO moocAsrDTO) {
        return ResUtil.success(moocService.moocItemAsr(MoocItemAsrParam.MoocItemAsrParamBuilder()
                .moocId(moocAsrDTO.getMoocId())
                .moocItemId(moocAsrDTO.getMoocItemId())
                .language(moocAsrDTO.getLanguage())
                .build()));
    }

    /**
     * 更新慕课ASR的结果
     * @param moocAsrInfoUpdateDTO
     * @return
     */
    @InnerAuth
    @PutMapping("asr")
    public ResData<String> updateAsrInfo(@RequestBody MoocAsrInfoUpdateDTO moocAsrInfoUpdateDTO) {
        return ResUtil.success(moocService.updateAsrInfo(moocAsrInfoUpdateDTO));
    }

    @GetMapping("asr")
    public ResData<MoocAsrTaskInfo> getMoocAsrTaskInfo(@Validated @NotNull(message = "慕课Item ID不能为空") Long moocItemId,
                                                       @Validated @NotNull(message = "慕课id不能为空") Long moocId) {
        return ResUtil.success(moocService.getMoocAsrTaskInfo(MoocItemQueryParam.MoocItemQueryParamBuilder()
                .moocItemId(moocItemId)
                .moocId(moocId)
                .build()));
    }

//    @GetMapping("{id}")
//    public ResData<>

}
