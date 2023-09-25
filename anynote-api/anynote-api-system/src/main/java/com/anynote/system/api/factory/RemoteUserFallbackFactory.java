package com.anynote.system.api.factory;

import com.anynote.system.api.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级
 * @author 称霸幼儿园
 */
@Component
@Slf4j
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {

    @Override
    public RemoteUserService create(Throwable throwable) {
        log.error("用户服务调用失败：{}", throwable.getMessage());
        return new RemoteUserService() {

        };
    }
}
