package com.anynote.note.api.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 批量删除慕课Item接口
 * @author 称霸幼儿园
 */
@Data
public class BatchDeleteMoocItemsDTO {

    /**
     * 慕课id
     */
    @NotNull(message = "慕课id不能为空")
    private Long moocId;

    /**
     * 慕课
     */
    @NotNull(message = "慕课item Ids不能为空")
    @NotEmpty(message = "慕课item Ids不能为空")
    private List<Long> itemIds;
}
