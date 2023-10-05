package com.anynote.system.controller;

import com.anynote.common.security.annotation.InnerAuth;
import com.anynote.core.web.model.bo.PageBean;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.system.api.model.bo.KnowledgeBaseImportUser;
import com.anynote.system.api.model.bo.LoginUser;
import com.anynote.system.api.model.dto.KnowledgeBaseUserImportDTO;
import com.anynote.system.api.model.po.SysUser;
import com.anynote.system.api.model.vo.KnowledgeBaseUserVO;
import com.anynote.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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
                                                                        @NotNull(message = "页面大小不能为空") Integer pageSize) {
        return ResData.success(sysUserService.getKnowledgeBaseUsers(knowledgeBaseId, page, pageSize));
    }
}
