package com.anynote.system.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImportFailUser {

    /**
     * 用户名
     */
    private String username;

    /**
     * 原因
     */
    private String reason;

}
