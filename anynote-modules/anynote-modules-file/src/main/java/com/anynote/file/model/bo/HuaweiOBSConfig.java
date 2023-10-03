package com.anynote.file.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 称霸幼儿园
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HuaweiOBSConfig implements Serializable {

    private static final long serialVersionUID = 2975271656230801861L;


    private String endPoint;

    private String accessKey;

    private String accessSecret;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 根路径
     */
    private String basePath;


}
