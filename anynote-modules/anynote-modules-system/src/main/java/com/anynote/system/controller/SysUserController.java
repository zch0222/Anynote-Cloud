package com.anynote.system.controller;

import com.anynote.common.datascope.annotation.RolePermissions;
import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.constant.Constants;
import com.anynote.core.enums.Role;
import com.anynote.core.exception.BusinessException;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.utils.StringUtils;
import com.anynote.core.web.model.bo.CreateResEntity;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.dto.BanUserDTO;
import com.anynote.system.api.model.dto.KnowledgeBaseUserImportDTO;
import com.anynote.system.api.model.dto.UnBanUserDTO;
import com.anynote.system.api.model.po.SysUser;
import com.anynote.system.api.model.vo.KnowledgeBaseUserVO;
import com.anynote.system.api.model.bo.SysUserQueryParam;
import com.anynote.system.api.model.dto.CreateUserDTO;
import com.anynote.system.model.dto.ResetPasswordDTO;
import com.anynote.system.model.vo.PublicUserInfoVO;
import com.anynote.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户信息 Controller
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("/user")
@Validated
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @InnerAuth
    @GetMapping("manageList")
    public ResData<PageBean<SysUser>> getManageUserList(@NotNull(message = "页码不能为空") Integer page,
                                                        @NotNull(message = "页面大小不能为空") Integer pageSize,
                                                        String username) {
        return ResUtil.success(sysUserService.getManageUserList(SysUserQueryParam.builder()
                        .page(page)
                        .pageSize(pageSize)
                        .username(username)
                .build()));
    }

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    @InnerAuth
    @GetMapping("/info/{username}")
    public ResData<LoginUser> getUserInfo(@PathVariable("username") String username) {
        return ResData.success(sysUserService.getUserInfo(username));
    }

    @PostMapping("bases/import")
    @InnerAuth
    public ResData<KnowledgeBaseUserImportDTO> importKnowledgeBaseUser(@Valid @RequestBody KnowledgeBaseUserImportDTO knowledgeBaseUserImportDTO) {
        return ResData.success(sysUserService.importKnowledgeBaseUser(knowledgeBaseUserImportDTO));
    }


    /**
     * 分页获取知识库
     * @param knowledgeBaseId
     * @param page
     * @param pageSize
     * @return
     */
    @InnerAuth
    @GetMapping("bases")
    public ResData<PageBean<KnowledgeBaseUserVO>> getKnowledgeBaseUsers(@NotNull(message = "知识库id不能为空") Long knowledgeBaseId,
                                                                        @NotNull(message = "页码不能为空") Integer page,
                                                                        @NotNull(message = "页面大小不能为空") Integer pageSize,
                                                                        String username) {
        return ResData.success(sysUserService.getKnowledgeBaseUsers(knowledgeBaseId, page, pageSize, username));
    }

    @InnerAuth
    @GetMapping("{userId}")
    public ResData<SysUser> getSysUserById(@PathVariable("userId") @NotNull(message = "用户id不能为空") Long userId) {
        return ResData.success(sysUserService.getSysUserById(userId));
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @InnerAuth
    @GetMapping("{userId}/info")
    public ResData<SysUser> getSysUserInfoById(@PathVariable("userId") @NotNull(message = "用户id不能为空") Long userId) {
        return ResData.success(sysUserService.getSysUserInfoById(userId));
    }

    @InnerAuth
    @PutMapping("{userId}")
    public ResData<Integer> updateSysUser(@PathVariable("userId") @NotNull(message = "用户id不能为空") Long userId,
                                         @RequestBody SysUser sysUser) {
        sysUser.setId(userId);
        return ResUtil.success(sysUserService.updateSysUser(sysUser));
    }

    @GetMapping("mine")
    public ResData<SysUser> getMyInfo() {
        return ResUtil.success(sysUserService.getMyUserInfo());
    }

    @RolePermissions(value = {Role.TEACHER})
    @PostMapping("resetPassword")
    public ResData<String> resetPassword(@Validated @RequestBody ResetPasswordDTO resetPasswordDTO) {
        return ResUtil.success(sysUserService.resetPassword(resetPasswordDTO));
    }

    @GetMapping("/pubInfo/{username}")
    public ResData<SysUser> getPublicUserInfo(@NotNull(message = "用户名不能为空") @PathVariable("username") String username) {
        return ResUtil.success(sysUserService.getPublicUserInfoByUsername(username));
    }

    @InnerAuth
    @PostMapping("")
    public ResData<CreateResEntity> createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        return ResUtil.success(CreateResEntity.builder()
                        .id(sysUserService.createUser(createUserDTO))
                .build());
    }

    @InnerAuth
    @PostMapping("banUser")
    public ResData<String> banUser(@Validated @RequestBody BanUserDTO banUserDTO) {
        sysUserService.banUser(banUserDTO.getUserId());
        return ResUtil.success(Constants.SUCCESS_RES);
    }

    @InnerAuth
    @PostMapping("unBanUser")
    public ResData<String> unBanUser(@Validated @RequestBody UnBanUserDTO unBanUserDTO) {
        sysUserService.unbanUser(unBanUserDTO.getUserId());
        return ResUtil.success(Constants.SUCCESS_RES);
    }

    /**
     * 查询用户
     * @param userId 用户id
     * @param username 用户名
     * @return
     */
    @GetMapping("publicUserInfo")
    public ResData<PublicUserInfoVO> getPublicUserInfo(Long userId, String username) {
        if (StringUtils.isNull(username) && StringUtils.isNull(userId)) {
            return ResUtil.success(null);
        }

        SysUser sysUser = sysUserService.getBaseMapper()
                .selectOne(new LambdaQueryWrapper<SysUser>()
                        .eq(StringUtils.isNotNull(userId), SysUser::getId, userId)
                        .eq(StringUtils.isNotNull(username), SysUser::getUsername, username)
                        .select(SysUser::getId, SysUser::getUsername,
                                SysUser::getNickname, SysUser::getAvatar));
        if (StringUtils.isNull(sysUser)) {
            return ResUtil.success(null);
        }
        return ResUtil.success(PublicUserInfoVO.builder()
                .userId(sysUser.getId())
                .username(sysUser.getUsername())
                .nickname(sysUser.getNickname())
                .avatar(sysUser.getAvatar())
                .build());

    }


}
