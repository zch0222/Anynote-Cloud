package com.anynote.note.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.anynote.ai.api.RemoteMoocVideoSummarizeService;
import com.anynote.ai.api.RemoteWhisperService;
import com.anynote.ai.api.enums.WhisperTaskStatus;
import com.anynote.ai.api.model.dto.GetMoocVideoSummarizesByMoocIdDTO;
import com.anynote.ai.api.model.dto.WhisperDTO;
import com.anynote.ai.api.model.po.MoocVideoSummarizePO;
import com.anynote.ai.api.model.vo.WhisperSubmitVO;
import com.anynote.common.datascope.annotation.RequiresPermissions;
import com.anynote.common.datascope.constants.PermissionConstants;
import com.anynote.common.elasticsearch.constant.ElasticsearchIndexConstants;
import com.anynote.common.elasticsearch.model.EsNoteIndex;
import com.anynote.common.elasticsearch.model.bo.EsMoocIndex;
import com.anynote.common.elasticsearch.model.bo.SearchPageBean;
import com.anynote.common.elasticsearch.utils.ElasticsearchUtil;
import com.anynote.common.redis.constant.RedisKey;
import com.anynote.common.redis.service.RedisService;
import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.constant.Constants;
import com.anynote.core.constant.FileConstants;
import com.anynote.core.constant.SecurityConstants;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.RemoteResDataUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.file.api.RemoteFileService;
import com.anynote.file.api.enums.FileSources;
import com.anynote.file.api.model.dto.OssSliceUploadTaskCreateDTO;
import com.anynote.file.api.model.dto.OssSliceUploadTaskCreatePublicDTO;
import com.anynote.file.api.model.vo.OssSliceUploadTaskVO;
import com.anynote.note.api.enums.KnowledgeBasePermissions;
import com.anynote.note.api.model.dto.MoocAsrInfoUpdateDTO;
import com.anynote.note.api.model.dto.MoocSearchDTO;
import com.anynote.note.api.model.vo.MoocVideoItemInfoVO;
import com.anynote.note.constant.MoocItemType;
import com.anynote.note.datascope.annotation.KnowledgeBaseDataScope;
import com.anynote.note.datascope.annotation.RequiresKnowledgeBasePermissions;
import com.anynote.note.mapper.MoocMapper;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.po.MoocItemPO;
import com.anynote.note.model.po.MoocItemTextPO;
import com.anynote.note.model.po.MoocPO;
import com.anynote.note.model.po.MoocVideoItemInfoPO;
import com.anynote.note.model.vo.*;
import com.anynote.note.service.MoocItemService;
import com.anynote.note.service.MoocItemTextService;
import com.anynote.note.service.MoocService;
import com.anynote.note.service.MoocVideoItemInfoService;
import com.anynote.system.api.model.bo.LoginUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 慕课服务实现类
 * @author 称霸幼儿园
 */
@Slf4j
@Service
public class MoocServiceImpl extends ServiceImpl<MoocMapper, MoocPO>
        implements MoocService {

    @Resource
    private TokenUtil tokenUtil;

    @Resource
    private RemoteFileService remoteFileService;

    @Resource
    private MoocItemService moocItemService;

    @Resource
    private MoocItemTextService moocItemTextService;

    @Resource
    private RemoteWhisperService remoteWhisperService;

    @Resource
    private RedisService redisService;

    @Resource
    private MoocVideoItemInfoService moocVideoItemInfoService;

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    private RemoteMoocVideoSummarizeService remoteMoocVideoSummarizeService;

    @RequiresPermissions(value = "n:mooc:read", paramIdName = "moocId", queryParamName = "moocQueryParam")
    @Override
    public MoocVO getMoocById(MoocQueryParam moocQueryParam) {
        MoocVO moocVO = this.baseMapper.selectMoocById(moocQueryParam.getMoocId());
        moocVO.setUserPermissions((Integer) moocQueryParam.getParams().get(PermissionConstants.PERMISSION_CONTEXT_KEY));
        return moocVO;
    }

    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.MANAGE,
            message = "没有权限创建慕课")
    @Override
    public Long createMooc(MoocCreateParam moocCreateParam) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        Date now = new Date();
        MoocPO moocPO = MoocPO.builder()
                .title(moocCreateParam.getTitle())
                .cover(moocCreateParam.getCover())
                .moocDescription(moocCreateParam.getMoocDescription())
                .dataScope(moocCreateParam.getDataScope())
                .knowledgeBaseId(moocCreateParam.getKnowledgeBaseId())
                .permissions("77400")
                .deleted(0)
                .updateBy(loginUser.getUserId())
                .createBy(loginUser.getUserId())
                .updateTime(now)
                .createTime(now)
                .build();
        this.save(moocPO);
        return moocPO.getId();
    }

    @RequiresPermissions(value = "n:mooc:update", paramIdName = "moocId", queryParamName = "moocUpdateParam")
    @Override
    public String updateMooc(MoocUpdateParam moocUpdateParam) {
        MoocPO moocPO = MoocPO.builder()
                .id(moocUpdateParam.getMoocId())
                .title(moocUpdateParam.getTitle())
                .cover(moocUpdateParam.getCover())
                .moocDescription(moocUpdateParam.getMoocDescription())
                .dataScope(moocUpdateParam.getDataScope())
                .knowledgeBaseId(moocUpdateParam.getKnowledgeBaseId())
                .permissions(moocUpdateParam.getMoocPermissions())
                .build();
        boolean res = updateById(moocPO);
        if (!res) {
            throw new BusinessException("更新失败");
        }
        return Constants.SUCCESS_RES;
    }

    @KnowledgeBaseDataScope(value = "n_mooc")
    @Override
    public PageBean<MoocListVO> getMoocList(MoocQueryParam moocQueryParam) {
        PageHelper.startPage(moocQueryParam.getPage(), moocQueryParam.getPageSize(), "update_time DESC");
        List<MoocListVO> moocListVOList = this.baseMapper.getMoocList(moocQueryParam);
        PageInfo<MoocListVO> pageInfo = new PageInfo<>(moocListVOList);
        return PageBean.<MoocListVO>builder()
                .current(moocQueryParam.getPage())
                .pages(pageInfo.getPages())
                .rows(moocListVOList)
                .total(pageInfo.getTotal())
                .build();
    }

    @Override
    public OssSliceUploadTaskVO createMoocCoverUploadTask(OssSliceUploadTaskCreatePublicDTO ossSliceUploadTaskCreatePublicDTO) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        return RemoteResDataUtil.getResData(remoteFileService
                .createOssSliceUploadTask(new OssSliceUploadTaskCreateDTO(ossSliceUploadTaskCreatePublicDTO,
                        StringUtils.format(FileConstants.MOOC_COVER_PATH_TEMPLATE, loginUser.getUserId()),
                        FileSources.MOOC_COVER.getValue())), "慕课封面上传任务创建失败");
    }

    @RequiresPermissions(value = "n:mooc:update", paramIdName = "moocId", queryParamName = "moocItemCreateParam")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createItems(MoocItemCreateParam moocItemCreateParam) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        List<MoocItemCreateParam.Item> items = moocItemCreateParam.getItems();
        Date now = new Date();
        Map<MoocItemCreateParam.Item, MoocItemPO> itemToPOMap = new HashMap<>();
        List<MoocItemPO> moocItemPOS = items.stream()
                .map(item -> {
                    MoocItemPO moocItemPO = MoocItemPO.builder()
                            .moocId(moocItemCreateParam.getMoocId())
                            .title(item.getTitle())
                            .objectName(item.getObjectName())
                            .parentId(item.getParentId())
                            .moocItemType(item.getMoocItemType())
                            .deleted(0)
                            .createBy(loginUser.getUserId())
                            .updateBy(loginUser.getUserId())
                            .createTime(now)
                            .updateTime(now)
                            .build();
                    itemToPOMap.put(item, moocItemPO);
                    return moocItemPO;
                })
                .collect(Collectors.toList());
        boolean res = moocItemService.saveBatch(moocItemPOS);
        if (!res) {
            throw new BusinessException("保存Item失败");
        }
        List<MoocItemTextPO> itemTextList = items.stream()
                .filter(item -> StringUtils.isNotEmpty(item.getItemText()))
                .map(item -> MoocItemTextPO.builder()
                        .moocItemId(itemToPOMap.get(item).getId())
                        .itemText(item.getItemText())
                        .deleted(0)
                        .createBy(loginUser.getUserId())
                        .updateBy(loginUser.getUserId())
                        .createTime(now)
                        .updateTime(now)
                        .build())
                .collect(Collectors.toList());
        List<MoocVideoItemInfoPO> moocVideoItemInfoPOList = moocItemPOS.stream()
                .filter(item -> item.getMoocItemType() == MoocItemType.VIDEO)
                .map(item -> MoocVideoItemInfoPO.builder()
                        .moocId(moocItemCreateParam.getMoocId())
                        .moocItemId(item.getId())
                        .createBy(loginUser.getUserId())
                        .updateBy(loginUser.getUserId())
                        .createTime(now)
                        .updateTime(now)
                        .deleted(0)
                        .build())
                .collect(Collectors.toList());

        moocVideoItemInfoService.saveBatch(moocVideoItemInfoPOList);

        if (!itemTextList.isEmpty()) {
            boolean textSaveRes = moocItemTextService.saveBatch(itemTextList);
            if (!textSaveRes) {
                throw new BusinessException("保存Item Text失败");
            }
        }
        return Constants.SUCCESS_RES;
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresPermissions(value = "n:mooc:update", paramIdName = "moocId", queryParamName = "moocItemUpdateParam")
    @Override
    public String updateMoocItem(MoocItemUpdateParam moocItemUpdateParam) {
        MoocItemPO moocItemPO = MoocItemPO.builder()
                .id(moocItemUpdateParam.getMoocItemId())
                .title(moocItemUpdateParam.getTitle())
                .objectName(moocItemUpdateParam.getObjectName())
                .parentId(moocItemUpdateParam.getParentId())
                .build();
        boolean moocItemUpdateRes = moocItemService.update(moocItemPO, new LambdaQueryWrapper<MoocItemPO>()
                .eq(MoocItemPO::getId, moocItemPO.getId())
                .eq(MoocItemPO::getMoocId, moocItemUpdateParam.getMoocId()));
        if (!moocItemUpdateRes) {
            throw new BusinessException("更新慕课Item失败");
        }
        if (StringUtils.isNotNull(moocItemUpdateParam.getItemText())) {
            MoocItemTextPO moocItemTextPO = MoocItemTextPO.builder()
                    .moocItemId(moocItemUpdateParam.getMoocItemId())
                    .itemText(moocItemUpdateParam.getItemText())
                    .build();
            boolean itemTextUpdateRes = moocItemTextService.saveOrUpdate(moocItemTextPO);
            if (!itemTextUpdateRes) {
                throw new BusinessException("慕课Item Text保存失败");
            }
        }
        return Constants.SUCCESS_RES;
    }

    @RequiresPermissions(value = "n:mooc:read", paramIdName = "moocId", queryParamName = "moocItemQueryParam")
    @Override
    public PageBean<MoocItemListVO> getMoocItemList(MoocItemQueryParam moocItemQueryParam) {
        PageHelper.startPage(moocItemQueryParam.getPage(), moocItemQueryParam.getPageSize())
                .setUnsafeOrderBy("CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(title, ' ', 1), '.', -1) AS UNSIGNED) ASC");
        List<MoocItemPO> moocItemPOList = moocItemService.list(new LambdaQueryWrapper<MoocItemPO>()
                .eq(MoocItemPO::getMoocId, moocItemQueryParam.getMoocId())
                .eq(MoocItemPO::getParentId, moocItemQueryParam.getParentId())
                .eq(StringUtils.isNotNull(moocItemQueryParam.getMoocItemType()),
                        MoocItemPO::getMoocItemType, moocItemQueryParam.getMoocItemType()));
        PageInfo<MoocItemPO> pageInfo = new PageInfo<>(moocItemPOList);
        List<MoocItemListVO> moocItemListVOList = moocItemPOList.stream()
                .map(MoocItemListVO::new)
                .collect(Collectors.toList());
        return PageBean.<MoocItemListVO>builder()
                .current(moocItemQueryParam.getPage())
                .pages(pageInfo.getPages())
                .rows(moocItemListVOList)
                .total(pageInfo.getTotal())
                .build();
    }

    @RequiresPermissions(value = "n:mooc:update", paramIdName = "moocId", queryParamName = "moocVideoCreateParam")
    @Override
    public OssSliceUploadTaskVO createMoocVideoUploadTask(MoocVideoCreateParam moocVideoCreateParam) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        return RemoteResDataUtil.getResData(remoteFileService
                .createOssSliceUploadTask(new OssSliceUploadTaskCreateDTO(moocVideoCreateParam.getOssSliceUploadTaskCreatePublicDTO(),
                        StringUtils.format(FileConstants.MOOC_VIDEO_PATH_TEMPLATE, loginUser.getUserId()),
                        FileSources.MOOC_COVER.getValue())), "慕课视频上传任务创建失败");
    }

    @RequiresPermissions(value = "n:mooc:update", paramIdName = "moocId", queryParamName = "moocItemAsrParam")
    @Override
    public MoocItemAsrVO moocItemAsr(MoocItemAsrParam moocItemAsrParam) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        MoocItemPO moocItemPO = moocItemService.getOne(new LambdaQueryWrapper<MoocItemPO>()
                .eq(MoocItemPO::getId, moocItemAsrParam.getMoocItemId())
                .eq(MoocItemPO::getMoocId, moocItemAsrParam.getMoocId()));
        if (StringUtils.isNull(moocItemPO)) {
            throw new BusinessException("Mooc Item不存在");
        }
        WhisperSubmitVO whisperSubmitVO = RemoteResDataUtil.getResData(remoteWhisperService
                .submitWhisperTask(WhisperDTO.builder().objectName(moocItemPO.getObjectName())
                        .language(moocItemAsrParam.getLanguage())
                        .build(), "inner"));
        MoocAsrTaskInfo taskInfo =  MoocAsrTaskInfo.builder()
                .taskId(whisperSubmitVO.getTaskId())
                .moocId(moocItemAsrParam.getMoocId())
                .moocItemId(moocItemAsrParam.getMoocItemId())
                .taskStatus(WhisperTaskStatus.STARTING)
                .userId(loginUser.getUserId())
                .build();
        redisService.setCacheObject(StringUtils.format(RedisKey.MOOC_ASR_TASK, whisperSubmitVO.getTaskId()),
                taskInfo);
        redisService.setCacheObject(StringUtils.format(RedisKey.MOOC_ASR_TASK_MOOC_ID_AND_MOOC_ITEM_ID_KEY,
                        moocItemAsrParam.getMoocId(),
                        moocItemAsrParam.getMoocItemId()),
                taskInfo);
        return MoocItemAsrVO.builder()
                .taskId(whisperSubmitVO.getTaskId())
                .build();
    }

    @Override
    public String updateAsrInfo(MoocAsrInfoUpdateDTO moocAsrInfoUpdateDTO) {
        MoocAsrTaskInfo moocAsrTaskInfo = redisService
                .getCacheObject(StringUtils.format(RedisKey.MOOC_ASR_TASK, moocAsrInfoUpdateDTO.getTaskId()));
        boolean res = moocVideoItemInfoService.update(new LambdaUpdateWrapper<MoocVideoItemInfoPO>()
                .eq(MoocVideoItemInfoPO::getMoocItemId, moocAsrTaskInfo.getMoocItemId())
                .set(MoocVideoItemInfoPO::getSrtObjectName, moocAsrInfoUpdateDTO.getSrtObjectName())
                .set(MoocVideoItemInfoPO::getUpdateTime, new Date())
                .set(MoocVideoItemInfoPO::getUpdateBy, 0L));
        if (!res) {
            throw new BusinessException(StringUtils.format("更新 moocVideoItemInfo，moocItemId={}失败",
                    moocAsrTaskInfo.getMoocItemId()));
        }
        return Constants.SUCCESS_RES;
    }

    @RequiresPermissions(value = "n:mooc:read", paramIdName = "moocId", queryParamName = "moocItemQueryParam")
    @Override
    public MoocAsrTaskInfo getMoocAsrTaskInfo(MoocItemQueryParam moocItemQueryParam) {
        return redisService
                .getCacheObject(StringUtils.format(RedisKey.MOOC_ASR_TASK_MOOC_ID_AND_MOOC_ITEM_ID_KEY,
                        moocItemQueryParam.getMoocId(),
                        moocItemQueryParam.getMoocItemId()));
    }

    @RequiresPermissions(value = "n:mooc:read", paramIdName = "moocId", queryParamName = "moocItemQueryParam")
    @Override
    public MoocVideoItemInfoVO getMoocVideoItemInfo(MoocItemQueryParam moocItemQueryParam) {
        MoocVideoItemInfoPO moocVideoItemInfoPO = moocVideoItemInfoService
                .getOne(new LambdaQueryWrapper<MoocVideoItemInfoPO>()
                        .eq(MoocVideoItemInfoPO::getMoocItemId, moocItemQueryParam.getMoocItemId())
                        .eq(MoocVideoItemInfoPO::getMoocId, moocItemQueryParam.getMoocId()));
        if (StringUtils.isNull(moocVideoItemInfoPO)) {
            throw new BusinessException(StringUtils.format("慕课Item id = {}，不存在视频信息", moocItemQueryParam.getMoocItemId()));
        }

        return MoocVideoItemInfoVO.builder()
                .id(moocVideoItemInfoPO.getId())
                .moocId(moocVideoItemInfoPO.getMoocId())
                .moocItemId(moocVideoItemInfoPO.getMoocItemId())
                .videoSummarize(moocVideoItemInfoPO.getVideoSummarize())
                .srtObjectName(moocVideoItemInfoPO.getSrtObjectName())
                .build();
    }

    @RequiresPermissions(value = "n:mooc:manage", paramIdName = "moocId", queryParamName = "moocParam")
    @Override
    public String deleteMooc(MoocParam moocParam) {
        boolean res = this.removeById(moocParam.getMoocId());
        if (!res) {
            throw new BusinessException("删除慕课失败");
        }
        return Constants.SUCCESS_RES;
    }

    @RequiresPermissions(value = "n:mooc:manage", paramIdName = "moocId", queryParamName = "moocItemBatchDeleteItemParam")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String batchDeleteMoocItems(MoocItemBatchDeleteItemParam moocItemBatchDeleteItemParam) {
        List<MoocItemPO> moocItemList = moocItemService.list(new LambdaQueryWrapper<MoocItemPO>()
                .eq(MoocItemPO::getMoocId, moocItemBatchDeleteItemParam.getMoocId())
                .select(MoocItemPO::getId, MoocItemPO::getMoocId, MoocItemPO::getParentId));
        Map<Long, List<MoocItemPO>> parentIdToMoocItem = new HashMap<>();
        for (MoocItemPO moocItemPO : moocItemList) {
            if (!parentIdToMoocItem.containsKey(moocItemPO.getParentId())) {
                List<MoocItemPO> moocItemPOList = new ArrayList<>();
                parentIdToMoocItem.put(moocItemPO.getParentId(), moocItemPOList);
            }
            parentIdToMoocItem.get(moocItemPO.getParentId()).add(moocItemPO);
            if (!parentIdToMoocItem.containsKey(moocItemPO.getId())) {
                parentIdToMoocItem.put(moocItemPO.getId(), new ArrayList<>());
            }
        }
        Set<Long> deleteItemsIds = new HashSet<>();
        for (Long itemId : moocItemBatchDeleteItemParam.getItemIds()) {
            if (!parentIdToMoocItem.containsKey(itemId)) {
                throw new BusinessException("无法删除慕课Item Id = " + itemId);
            }
            if (deleteItemsIds.contains(itemId)) {
                continue;
            }
            Deque<Long> idDeque = new ArrayDeque<>();
            idDeque.addLast(itemId);
            while (!idDeque.isEmpty()) {
                Long id = idDeque.removeFirst();
                deleteItemsIds.add(id);
                idDeque.addAll(parentIdToMoocItem.get(id).stream()
                        .map(MoocItemPO::getId)
                        .filter(moocItemPOId -> !deleteItemsIds.contains(moocItemPOId))
                        .collect(Collectors.toList()));
            }
        }
        moocItemService.removeByIds(deleteItemsIds);
        return Constants.SUCCESS_RES;
    }

//    @RequiresPermissions(value = "n:mooc:update", paramIdName = "moocId", queryParamName = "moocItemCreateParam")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createSingleItem(MoocItemCreateParam moocItemCreateParam) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        Date now = new Date();
        MoocItemCreateParam.Item item = moocItemCreateParam.getItems().get(0);
        MoocItemPO moocItemPO = MoocItemPO.builder()
                .moocId(moocItemCreateParam.getMoocId())
                .title(item.getTitle())
                .objectName(item.getObjectName())
                .parentId(item.getParentId())
                .moocItemType(item.getMoocItemType())
                .deleted(0)
                .createBy(loginUser.getUserId())
                .updateBy(loginUser.getUserId())
                .createTime(now)
                .updateTime(now)
                .build();
        moocItemService.save(moocItemPO);
        if (StringUtils.isNotNull(item.getItemText())) {
            MoocItemTextPO moocItemTextPO = MoocItemTextPO.builder()
                    .moocItemId(moocItemPO.getId())
                    .itemText(item.getItemText())
                    .deleted(0)
                    .createBy(loginUser.getUserId())
                    .updateBy(loginUser.getUserId())
                    .createTime(now)
                    .updateTime(now)
                    .build();
            moocItemTextService.save(moocItemTextPO);
        }
        if (MoocItemType.VIDEO == item.getMoocItemType()) {
            MoocVideoItemInfoPO moocVideoItemInfoPO = MoocVideoItemInfoPO.builder()
                    .moocId(moocItemCreateParam.getMoocId())
                    .moocItemId(moocItemPO.getId())
                    .createBy(loginUser.getUserId())
                    .updateBy(loginUser.getUserId())
                    .createTime(now)
                    .updateTime(now)
                    .deleted(0)
                    .build();
            moocVideoItemInfoService.save(moocVideoItemInfoPO);
        }
        return moocItemPO.getId();
//        return null;
    }

    @Override
    public SearchPageBean<EsMoocIndex> searchMooc(MoocSearchDTO moocSearchDTO) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        List<Long> moocIds = this.getVisibleMoocIds(loginUser.getUserId());
        // 数据范围
        Query moocIdQuery = TermsQuery.of(t -> t
                .field("id")
                .terms(TermsQueryField.of(f -> f.value(moocIds.stream()
                        .map(FieldValue::of)
                        .collect(Collectors.toList()))))
        )._toQuery();
        // 未删除
        Query notDeletedQuery = TermQuery.of(t -> t
                .field("deleted")
                .value(0))
                ._toQuery();
        // 关键词查询
        Query keywordQuery = MatchQuery.of(q -> q
                .field("all")
                .query(moocSearchDTO.getKeyword()))
                ._toQuery();
        Query query = BoolQuery.of(b -> b.must(Arrays.asList(moocIdQuery, notDeletedQuery, keywordQuery)))._toQuery();
        log.info("MOOC QUERY: {}", query.toString());
        int from = (moocSearchDTO.getPage()-1) * moocSearchDTO.getPageSize();
        try {
            SearchResponse<EsMoocIndex> searchResponse = elasticsearchClient.search(s -> s
                            .index(ElasticsearchIndexConstants.MOOC_INDEX)
                            .query(query)
                            .from(from)
                            .size(moocSearchDTO.getPageSize())
                            .highlight(h -> h
                                    .requireFieldMatch(false)
                                    .fields("title", hBuilder -> hBuilder)
                                    .fragmentSize(20)),
                    EsMoocIndex.class);
            return ElasticsearchUtil.buildSearchPageBean(searchResponse, EsMoocIndex.class, moocSearchDTO.getPageSize(), moocSearchDTO.getPage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("搜索慕课失败");
        }
    }

    @Override
    public List<Long> getVisibleMoocIds(Long userId) {
        return this.baseMapper.selectMoocIds(userId);
    }

    @RequiresPermissions(value = "n:mooc:read", paramIdName = "moocId", queryParamName = "moocItemQueryParam")
    @Override
    public List<MoocVideoSummarizePO> getMoocVideoSummarize(MoocItemQueryParam moocItemQueryParam) {
        log.info(moocItemQueryParam.getMoocItemId().toString());
        GetMoocVideoSummarizesByMoocIdDTO getMoocVideoSummarizesByMoocIdDTO = new GetMoocVideoSummarizesByMoocIdDTO();
        getMoocVideoSummarizesByMoocIdDTO.setMoocItemId(moocItemQueryParam.getMoocItemId());
        getMoocVideoSummarizesByMoocIdDTO.setMoocId(moocItemQueryParam.getMoocId());

        return RemoteResDataUtil.getResData(remoteMoocVideoSummarizeService.getMoocVideoSummarizesByMoocItemId(SecurityConstants.INNER, getMoocVideoSummarizesByMoocIdDTO));
    }
}