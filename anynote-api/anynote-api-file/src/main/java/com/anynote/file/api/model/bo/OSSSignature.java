package com.anynote.file.api.model.bo;

import com.anynote.file.api.enums.OSSSignatureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OSSSignature {

    private OSSSignatureType type;

    private OSSSignatureData credentials;
}
