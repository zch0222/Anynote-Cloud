package com.anynote.canal.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class CanalMessageUtil {

    /**
     * 表名key
     */
    public static final String TABLE_TABLE_NAME_KEY = "table";

    /**
     * 数据 key
     */
    public static final String DATA_KEY = "data";

    /**
     * 操作类型 key
     */
    public static final String TYPE_KEY = "type";

    /**
     * database name key
     */
    public static final String DATABASE_NAME_KEY = "database";

    public static String getTableName(Map<String, Object> message) {
        return String.valueOf(message.get(TABLE_TABLE_NAME_KEY));
    }

    public static <T> List<T> getData(Map<String, Object> message, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(message.get(DATA_KEY)),
                new TypeToken<List<T>>() {}.getType());
    }

    public static String getType(Map<String, Object> message) {
        return String.valueOf(message.get(TYPE_KEY));
    }

    public static String getDatabase(Map<String, Object> message) {
        return String.valueOf(message.get(DATABASE_NAME_KEY));
    }

}
