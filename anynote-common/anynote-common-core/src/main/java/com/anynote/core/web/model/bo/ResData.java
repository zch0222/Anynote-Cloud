package com.anynote.core.web.model.bo;

import com.anynote.core.serialization.NullObjectJsonSerializer;
import com.anynote.core.web.enums.ResCode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 操作消息提醒
 * @author 称霸幼儿园
 */
@Data
@NoArgsConstructor
public class ResData<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS = ResCode.SUCCESS.getCode();

    private String code;

    private String msg;

    @JsonSerialize(nullsUsing = NullObjectJsonSerializer.class)
    private T data;

    public ResData(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功
     * @param data
     * @return
     * @param <K>
     */
    public static <K> ResData<K> success(K data) {
        ResData<K> resData = new ResData<>(ResCode.SUCCESS.getCode(), ResCode.SUCCESS.getMsg(), data);
        return resData;
    }

    /**
     * 失败
     * @param resCode
     * @return
     * @param <K>
     */
    public static <K> ResData<K> error(ResCode resCode, K data) {
        ResData<K> resData = new ResData<>(resCode.getCode(), resCode.getMsg(), data);
        return resData;
    }

    public static <K> ResData<K> error(ResCode resCode) {
        ResData resData = new ResData<>(resCode.getCode(), resCode.getMsg(), null);
        return resData;
    }

    public static <K> ResData<K> error(ResCode resCode, String msg, K data) {
        ResData<K> resData = new ResData<>(resCode.getCode(), msg, data);
        return resData;
    }

    public static <K> ResData<K> error(ResCode resCode, String msg) {
        ResData resData = new ResData<>(resCode.getCode(), msg, null);
        return resData;
    }

}
