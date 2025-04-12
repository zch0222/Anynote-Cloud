package com.anynote.system.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncreaseApiUsageDTO {

    /**
     * 时间
     */
    @NotNull(message = "时间不能为空")
    private Date time;

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空")
    private Integer type;
}
