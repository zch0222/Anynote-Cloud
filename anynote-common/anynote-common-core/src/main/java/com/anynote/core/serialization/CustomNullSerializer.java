package com.anynote.core.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author 称霸幼儿园
 */
public class CustomNullSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            // 对象为 null 时返回空对象 {}
            gen.writeStartObject();
            gen.writeEndObject();
        } else {
            // 使用默认序列化方式
            serializers.defaultSerializeValue(value, gen);
        }
    }
}