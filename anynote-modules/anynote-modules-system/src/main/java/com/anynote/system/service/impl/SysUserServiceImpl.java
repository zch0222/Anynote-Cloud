package com.anynote.system.service.impl;

import com.anynote.common.security.token.TokenUtil;
import com.anynote.common.security.utils.SecurityUtils;
import com.anynote.core.constant.Constants;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.enums.ResCode;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.system.api.model.bo.KnowledgeBaseImportUser;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.bo.Token;
import com.anynote.system.api.model.dto.KnowledgeBaseUserImportDTO;
import com.anynote.system.api.model.po.SysUser;
import com.anynote.system.api.model.vo.KnowledgeBaseUserVO;
import com.anynote.system.mapper.SysUserMapper;
import com.anynote.system.service.SysOrganizationService;
import com.anynote.system.service.SysPermissionService;
import com.anynote.system.service.SysRoleService;
import com.anynote.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户服务
 * @author 称霸幼儿园
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysOrganizationService sysOrganizationService;

    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor;

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return
     */
    @Override
    public LoginUser getUserInfo(String username) {
        SysUser sysUser = this.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser)) {
            throw new BusinessException(ResCode.USER_NOT_FOUND);
        }

        // 获取角色的权限列表
        Set<String> permissions = sysPermissionService.getRolePermission(sysUser);

        String roleKey = sysRoleService.selectRoleKeysByUserId(sysUser.getId());

        return new LoginUser(sysUser, permissions, roleKey, true);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return this.baseMapper.selectUserByUserName(userName);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public KnowledgeBaseUserImportDTO importKnowledgeBaseUser(KnowledgeBaseUserImportDTO knowledgeBaseUserImportDTO) {
        Integer[] failCount = {0};
        List<String> failUserNameList = new ArrayList<>();
        String password = RandomStringUtils.randomNumeric(10);
        List<KnowledgeBaseImportUser> knowledgeBaseImportUserList = knowledgeBaseUserImportDTO.getKnowledgeBaseImportUserList().stream()
                .map(knowledgeBaseImportUser -> {
                    SysUser sysUser = new SysUser();
                    sysUser.setUsername(knowledgeBaseImportUser.getUsername());
                    sysUser.setNickname(knowledgeBaseImportUser.getNickname());
                    sysUser.setCreateBy(knowledgeBaseImportUser.getCreateBy());
                    sysUser.setCreateTime(knowledgeBaseImportUser.getCreateTime());
                    sysUser.setUpdateBy(knowledgeBaseImportUser.getUpdateBy());
                    sysUser.setUpdateTime(knowledgeBaseImportUser.getUpdateTime());
                    sysUser.setStatus(0);
                    sysUser.setSex(2);
                    sysUser.setDeleted(0);
                    sysUser.setPassword(SecurityUtils.encryptPassword(password));
                    if (false == checkUsername(sysUser.getUsername())) {
                        knowledgeBaseImportUser.setPassword("用户名已经存在");
                        failUserNameList.add(knowledgeBaseImportUser.getUsername());
                        failCount[0] = failCount[0] + 1;
                    }
                    else {
                        this.baseMapper.insert(sysUser);
                        asyncExecutor.submit(() -> associateUserRole(sysUser.getId(), 2L));
                        sysUser.setPassword(password);
                        knowledgeBaseImportUser.setPassword(password);
                        knowledgeBaseImportUser.setUserId(sysUser.getId());
                    }


                    return knowledgeBaseImportUser;
                }).collect(Collectors.toList());
        // 异步执行
        asyncExecutor.submit(() -> {
            sysOrganizationService.associateKnowledgeUserOrganization(knowledgeBaseImportUserList);
        });
        knowledgeBaseUserImportDTO.setFailCount(failCount[0]);
        knowledgeBaseUserImportDTO.setFailUserNameList(failUserNameList);
        return knowledgeBaseUserImportDTO;
    }

    @Override
    public Integer associateUserRole(Long userId, Long roleId) {
        return this.baseMapper.insertUserRole(userId, roleId);
    }

    private boolean checkUsername(String username) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper
                .eq(SysUser::getUsername, username)
                .select(SysUser::getUsername);
        return StringUtils.isNull(this.baseMapper.selectOne(sysUserLambdaQueryWrapper));
    }

    @Override
    public PageBean<KnowledgeBaseUserVO> getKnowledgeBaseUsers(Long knowledgeBaseId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, "user_id asc");
        List<KnowledgeBaseUserVO> knowledgeBaseUserVOList = this.baseMapper.selectKnowledgeBaseUsers(knowledgeBaseId);
        PageInfo<KnowledgeBaseUserVO> pageInfo = new PageInfo<>(knowledgeBaseUserVOList);
        return PageBean.<KnowledgeBaseUserVO>builder()
                .rows(knowledgeBaseUserVOList)
                .pages(pageInfo.getPages())
                .total(pageInfo.getTotal())
                .build();
    }

    @Override
    public SysUser getSysUserById(Long userId) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper
                .eq(SysUser::getId, userId)
                .select(SysUser::getUsername, SysUser::getPassword);
        return this.baseMapper.selectOne(sysUserLambdaQueryWrapper);
    }

    @Override
    public Integer updateSysUser(SysUser sysUser) {
        return this.baseMapper.updateById(sysUser);
    }
}
