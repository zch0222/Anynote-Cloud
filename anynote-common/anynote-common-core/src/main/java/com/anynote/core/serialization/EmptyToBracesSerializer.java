package com.anynote.core.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * JackJson空对象序列化器
 * @author 称霸幼儿园
 */
public class EmptyToBracesSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value == null || value.isEmpty()) {
            jsonGenerator.writeString("{}");
        } else {
            jsonGenerator.writeString(value);
        }
    }
}
