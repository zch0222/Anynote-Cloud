package com.anynote.canal.model.bo;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author 称霸幼儿园
 */
public class CanalMessage {

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

    private final Map<String, Object> message;

    private final String messageSrt;

    private final Gson gson = new Gson();


    public CanalMessage(byte[] messageBody) {
        messageSrt = new String(messageBody, StandardCharsets.UTF_8);
        this.message = gson.fromJson(messageSrt,
                new TypeToken<Map<String, Object>>() {}.getType());
    }

    public String getMessageSrt() {
        return messageSrt;
    }

    public String getTableName() {
        return (String) message.get(TABLE_TABLE_NAME_KEY);
    }

    public String getType() {
        return (String) message.get(TYPE_KEY);
    }

    public String getDatabase() {
        return (String) message.get(DATABASE_NAME_KEY);
    }

    public <T> List<T> getData(Class<T> clazz) {
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setFieldNamingStrategy(new FieldNamingStrategy() {
                    @Override
                    public String translateName(Field field) {
                        // 特殊处理 deleted 字段
                        if (field.getName().equals("deleted")) {
                            return "is_delete";
                        }
                        // 其他字段使用默认策略
                        return FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
                    }
                })
                .create()
                .fromJson(gson.toJson(message.get(DATA_KEY)), type);
    }

}
