package com.anynote.manage.service.impl;

import com.anynote.common.redis.constant.RedisKey;
import com.anynote.common.redis.service.RedisService;
import com.anynote.manage.model.vo.CacheVO;
import com.anynote.manage.service.ManageCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManageCacheServiceImpl implements ManageCacheService {

    @Resource
    private RedisService redisService;

    @Override
    public List<CacheVO> getCaches() {
        List<CacheVO> caches = new ArrayList<>();
        Map<String, Object> taskMap = redisService.getObjects("task:");
        caches.add(CacheVO.builder()
                .cacheName("分片上传任务")
                .cacheKey(RedisKey.OSS_SLICE_UPLOAD_TASK)
                .cacheMap(taskMap)
                .build());
        Map<String, Object> ossSliceUploadTaskFinishedSliceIndexSetMap = redisService.getObjects("oss_slice_upload_task_finished_slice_index_set:");
        caches.add(CacheVO.builder()
                .cacheName("Oss已经上传完成的分片set")
                .cacheKey(RedisKey.OSS_SLICE_UPLOAD_TASK_FINISHED_SLICE_INDEX_SET)
                .cacheMap(ossSliceUploadTaskFinishedSliceIndexSetMap)
                .build());
        Map<String, Object> ossObjectUrlMap = redisService.getObjects("oss_object_url:");
        caches.add(CacheVO.builder()
                .cacheName("文件对象URL")
                .cacheKey(RedisKey.OSS_OBJECT_URL)
                .cacheMap(ossObjectUrlMap)
                .build());
        Map<String, Object> moocASRTaskMap = redisService.getObjects("mooc_asr_task:taskId:");
        caches.add(CacheVO.builder()
                .cacheName("慕课ASR任务")
                .cacheKey(RedisKey.OSS_OBJECT_URL)
                .cacheMap(moocASRTaskMap)
                .build());
        return caches;
    }
}
