package com.anynote.note.service.impl;

import com.anynote.common.datascope.annotation.RequiresPermissions;
import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.constant.Constants;
import com.anynote.core.constant.FileConstants;
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
import com.anynote.note.datascope.annotation.KnowledgeBaseDataScope;
import com.anynote.note.datascope.annotation.RequiresKnowledgeBasePermissions;
import com.anynote.note.mapper.MoocMapper;
import com.anynote.note.model.bo.*;
import com.anynote.note.model.po.MoocItemPO;
import com.anynote.note.model.po.MoocItemTextPO;
import com.anynote.note.model.po.MoocPO;
import com.anynote.note.model.vo.MoocItemListVO;
import com.anynote.note.model.vo.MoocItemVO;
import com.anynote.note.model.vo.MoocListVO;
import com.anynote.note.service.MoocItemService;
import com.anynote.note.service.MoocItemTextService;
import com.anynote.note.service.MoocService;
import com.anynote.system.api.model.bo.LoginUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 慕课服务实现类
 * @author 称霸幼儿园
 */
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
        boolean textSaveRes = moocItemTextService.saveBatch(itemTextList);
        if (!textSaveRes) {
            throw new BusinessException("保存Item Text失败");
        }
        return Constants.SUCCESS_RES;
    }

    @RequiresPermissions(value = "n:mooc:read", paramIdName = "moocId", queryParamName = "moocItemQueryParam")
    @Override
    public PageBean<MoocItemListVO> getMoocItemList(MoocItemQueryParam moocItemQueryParam) {
        PageHelper.startPage(moocItemQueryParam.getPage(), moocItemQueryParam.getPageSize(), "update_time DESC");
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
}