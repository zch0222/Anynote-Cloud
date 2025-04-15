package com.anynote.manage.controller;

import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.manage.model.vo.CacheVO;
import com.anynote.manage.service.ManageCacheService;
import com.anynote.system.api.model.bo.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 缓存管理
 * @author 称霸幼儿园
 */
@RestController
@RequestMapping("caches")
public class ManageCacheController {

    @Resource
    private ManageCacheService manageCacheService;


    /**
     * 获取所有缓存
     * @return
     */
    @GetMapping("all")
    public ResData<List<CacheVO>> getAllCaches() {
        return ResUtil.success(manageCacheService.getCaches());
    }

    /**
     * 在线用户
     * @return
     */
    @GetMapping("onlineUsers")
    public ResData<CacheVO<LoginUser>> getOnlineUsers() {
        return ResUtil.success(manageCacheService.getOnlineUsers());
    }
}
