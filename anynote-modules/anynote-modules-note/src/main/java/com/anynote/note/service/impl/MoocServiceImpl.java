package com.anynote.note.service.impl;

import com.anynote.common.datascope.enums.PermissionEnum;
import com.anynote.common.security.token.TokenUtil;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.PermissionEntity;
import com.anynote.note.api.enums.KnowledgeBasePermissions;
import com.anynote.note.datascope.annotation.KnowledgeBaseDataScope;
import com.anynote.note.datascope.annotation.RequiresKnowledgeBasePermissions;
import com.anynote.note.mapper.MoocMapper;
import com.anynote.note.model.bo.MoocCreateParam;
import com.anynote.note.model.bo.MoocQueryParam;
import com.anynote.note.model.po.MoocPO;
import com.anynote.note.model.vo.MoocListVO;
import com.anynote.note.service.MoocService;
import com.anynote.system.api.model.bo.LoginUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 慕课服务实现类
 * @author 称霸幼儿园
 */
@Service
public class MoocServiceImpl extends ServiceImpl<MoocMapper, MoocPO>
        implements MoocService {

    @Resource
    private TokenUtil tokenUtil;

    @RequiresKnowledgeBasePermissions(value = KnowledgeBasePermissions.MANAGE,
            message = "没有权限创建慕课")
    @Override
    public Long createMooc(MoocCreateParam moocCreateParam) {
        LoginUser loginUser = tokenUtil.getLoginUser();
        Date now = new Date();
        MoocPO moocPO = MoocPO.builder()
                .title(moocCreateParam.getTitle())
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
}