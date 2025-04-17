package com.anynote.manage.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheVO<T> {

    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 缓存Key
     */
    private String cacheKey;

    /**
     * 缓存数量
     */
    private Integer cacheCount;

    /**
     * cache Map
     */
    private Map<String, T> cacheMap;
}
