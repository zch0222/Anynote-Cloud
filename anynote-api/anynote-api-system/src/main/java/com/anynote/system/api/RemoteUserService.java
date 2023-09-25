package com.anynote.system.api;

import com.anynote.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 用户服务远程调用
 *
 * @author 称霸幼儿园
 */
@FeignClient(contextId = "remoteUserService",
        value = ServiceNameConstants.SYSTEM_SERVICE)
public class RemoteUserService {

}
