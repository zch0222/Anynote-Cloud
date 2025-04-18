package com.anynote.note.controller;

import com.anynote.ai.api.model.dto.GetMoocVideoSummarizesByMoocIdDTO;
import com.anynote.ai.api.model.po.MoocVideoSummarizePO;
import com.anynote.common.elasticsearch.model.bo.EsMoocIndex;
import com.anynote.common.elasticsearch.model.bo.SearchPageBean;
import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.file.api.model.dto.OssSliceUploadTaskCreatePublicDTO;
import com.anynote.file.api.model.vo.OssSliceUploadTaskVO;
import com.anynote.note.api.model.dto.BatchDeleteMoocItemsDTO;
import com.anynote.note.api.model.dto.MoocAsrInfoUpdateDTO;
import com.anynote.note.api.model.dto.MoocSearchDTO;
import com.anynote.note.api.model.vo.MoocVideoItemInfoVO;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.dto.*;
import com.anynote.note.model.vo.*;
import com.anynote.note.service.MoocItemService;
import com.anynote.note.service.MoocService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
//import java.util.Collections;
//import java.util.stream.Collectors;

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

    /**
     * 删除慕课
     * @param moocId 慕课id
     * @return
     */
    @DeleteMapping("/{moocId}")
    public ResData<String> deleteMooc(@PathVariable Long moocId) {
        return ResUtil.success(moocService.deleteMooc(MoocParam.MoocParamBuilder()
                .moocId(moocId)
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
     * 批量创建moocItems
     * @param createDTO
     * @return
     */
    @PostMapping("itemsCreateBatch")
    public ResData<String> createMoocItems(@Validated @RequestBody MoocItemCreateDTO createDTO) {
        return ResUtil.success(moocService.createItems(MoocItemCreateParam.MoocItemCreateParamBuilder()
                .moocId(createDTO.getMoocId())
                .knowledgeBaseId(createDTO.getKnowledgeBaseId())
                .items(createDTO.getItems())
                .build()));
    }

    /**
     * 创建单个Item
     * @param createDTO
     * @return
     */
    @PostMapping("items")
    public ResData<Long> createMoocItem(@Validated @RequestBody MoocItemSingleCreateDTO createDTO) {
        return ResUtil.success(moocService.createSingleItem(MoocItemCreateParam.MoocItemCreateParamBuilder()
                .moocId(createDTO.getMoocId())
                .items(Collections.singletonList(createDTO.getItem()))
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
     * 批量删除慕课Item
     * @param batchDeleteMoocItemsDTO
     * @return
     */
    @PostMapping("batchDeleteItems")
    public ResData<String> batchDeleteMoocItems(@RequestBody @Validated BatchDeleteMoocItemsDTO batchDeleteMoocItemsDTO) {
        return ResUtil.success(moocService.batchDeleteMoocItems(MoocItemBatchDeleteItemParam
                .MoocItemBatchDeleteItemParamBuilder()
                .itemIds(batchDeleteMoocItemsDTO.getItemIds())
                .moocId(batchDeleteMoocItemsDTO.getMoocId())
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

    @GetMapping("videoItemInfo")
    public ResData<MoocVideoItemInfoVO> getMoocVideoItemInfo(@Validated @NotNull(message = "慕课Item ID不能为空") Long moocItemId,
                                                             @Validated @NotNull(message = "慕课id不能为空") Long moocId) {
        return ResUtil.success(moocService.getMoocVideoItemInfo(MoocItemQueryParam.MoocItemQueryParamBuilder()
                .moocId(moocId)
                .moocItemId(moocItemId)
                .build()));
    }

    /**
     * 获取慕课视频AI总结
     * @param moocVideoSummarizesDTO
     * @return
     */
    @GetMapping("videoSummarizes")
    public ResData<List<MoocVideoSummarizePO>> getMoocVideoSummarize(@Validated GetMoocVideoSummarizesByMoocIdDTO moocVideoSummarizesDTO) {
        return ResUtil.success(moocService.getMoocVideoSummarize(MoocItemQueryParam.MoocItemQueryParamBuilder()
                .moocId(moocVideoSummarizesDTO.getMoocId())
                .moocItemId(moocVideoSummarizesDTO.getMoocItemId())
                .build()));
    }

    /**
     * 搜索慕课
     * @param moocSearchDTO
     * @return
     */
    @GetMapping("search")
    public ResData<SearchPageBean<EsMoocIndex>> searchMooc(@Validated MoocSearchDTO moocSearchDTO) {
        return ResUtil.success(moocService.searchMooc(moocSearchDTO));
    }

//    @GetMapping("{id}")
//    public ResData<>

}
