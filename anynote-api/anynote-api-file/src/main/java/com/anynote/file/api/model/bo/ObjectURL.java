package com.anynote.file.api.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectURL {

    /**
     * 对象URL
     */
    private String url;

    /**
     * 过期时间
     */
    private Date expireTime;
}
