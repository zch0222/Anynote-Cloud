package com.anynote.common.redis.constant;

/**
 * Redis 前缀
 */
public class RedisKey {

    /**
     * Oss 分片上传任务前缀 <br/>
     * 第一个参数是用户id，第二个参数是uploadId
     */
    public static final String OSS_SLICE_UPLOAD_TASK = "task:{}:oss_slice_upload_task:{}";


    /**
     * Oss已经上传完成的分片set <br/>
     * 参数是uploadId
     */
    public static final String OSS_SLICE_UPLOAD_TASK_FINISHED_SLICE_INDEX_SET = "oss_slice_upload_task_finished_slice_index_set:{}";

    /**
     * 文件对象URL <br/>
     * 参数是对象名称
     */
    public static final String OSS_OBJECT_URL = "oss_object_url:{}";

    /**
     * 慕课ASR任务 <br/>
     * 参数是任务id
     */
    public static final String MOOC_ASR_TASK = "mooc_asr_task:taskId:{}";

    /**
     * 第一个参数是慕课id
     * 第二个参数是慕课item id
     */
    public static final String MOOC_ASR_TASK_MOOC_ID_AND_MOOC_ITEM_ID_KEY = "mooc_asr_task:mooc_id:{}:mooc_item_id:{}";
}
