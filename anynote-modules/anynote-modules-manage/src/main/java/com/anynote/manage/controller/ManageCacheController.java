package com.anynote.manage.controller;

import com.anynote.common.redis.service.RedisService;
import com.anynote.core.constant.Constants;
import com.anynote.core.utils.ResUtil;
import com.anynote.core.web.model.bo.ResData;
import com.anynote.manage.model.vo.CacheVO;
import com.anynote.manage.model.vo.OnlineUserVO;
import com.anynote.manage.service.ManageCacheService;
import com.anynote.system.api.model.bo.LoginUser;
import org.springframework.web.bind.annotation.*;

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

    @Resource
    private RedisService redisService;

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
    public ResData<List<OnlineUserVO>> getOnlineUsers() {
        return ResUtil.success(manageCacheService.getOnlineUsers());
    }

    /**
     * 删除缓存
     * @param cacheKey 缓存键
     * @return
     */
    @DeleteMapping("{cacheKey}")
    public ResData<String> deleteCache(@PathVariable("cacheKey") String cacheKey) {
        redisService.deleteObject(cacheKey);
        return ResUtil.success(Constants.SUCCESS_RES);
    }
}
