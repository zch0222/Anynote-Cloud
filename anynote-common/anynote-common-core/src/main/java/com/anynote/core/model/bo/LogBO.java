package com.anynote.core.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogBO {

    /**
     * url
     */
    private String url;

    private String method;

    private String ip;

    private String requestArgs;

    private String response;

    /**
     * 是否成功 1.成功 0.失败
     */
    private Integer success;


    private String errorMsg;

    /**
     * 花费时间（ms）
     */
    private Long timeConsuming;

    private Long userId;

    private String userName;

    private String nickName;
}
