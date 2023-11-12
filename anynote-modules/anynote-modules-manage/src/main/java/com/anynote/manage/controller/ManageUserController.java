package com.anynote.manage.controller;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.manage.service.ManageUserService;
import com.anynote.system.api.model.bo.SysUserUpdateParam;
import com.anynote.system.api.model.po.SysUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 管理用户
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("users")
@Validated
public class ManageUserController {

    @Resource
    private ManageUserService manageUserService;

    /**
     * 获取用户列表
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping
    public ResData<PageBean<SysUser>> getUserList(@NotNull(message = "页码不能为空") Integer page,
                                                  @NotNull(message = "页面大小不能为空") Integer pageSize) {
        return ResUtil.success(manageUserService.getUserList(page, pageSize));
    }


    @GetMapping("{userId}")
    public ResData<SysUser> getSysUserInfoById(@NotNull(message = "用户id不能为空") @PathVariable("userId") Long userId) {
        return ResUtil.success(manageUserService.getSysUserInfoById(userId));
    }

    @PatchMapping("{userId}")
    public ResData<Integer> updateSysUser(@NotNull(message = "用户id不能为空") @PathVariable("userId") Long userId,
                                         @NotNull(message = "用户信息不能为空") @RequestBody SysUserUpdateParam updateParam) {
        return ResUtil.success(manageUserService.updateSysUser(updateParam));
    }



}
