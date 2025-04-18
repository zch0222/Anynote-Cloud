package com.anynote.manage.service;

import com.anynote.manage.model.vo.CacheVO;
import com.anynote.manage.model.vo.OnlineUserVO;
import com.anynote.system.api.model.bo.LoginUser;

import java.util.List;

public interface ManageCacheService {

    public List<CacheVO> getCaches();

    public List<OnlineUserVO> getOnlineUsers();


}
