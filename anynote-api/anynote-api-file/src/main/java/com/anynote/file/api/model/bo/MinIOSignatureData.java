package com.anynote.file.api.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * MinIO临时证书数据
 * @author 称霸幼儿园
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MinIOSignatureData implements OSSSignatureData {

    private String url;

    private Date expirationDate;
}
