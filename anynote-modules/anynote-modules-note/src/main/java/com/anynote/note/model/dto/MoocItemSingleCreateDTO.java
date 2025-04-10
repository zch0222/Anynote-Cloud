package com.anynote.note.model.dto;

import com.anynote.note.model.bo.MoocItemCreateParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 单独创建慕课ItemDTO
 * @author 称霸幼儿园
 */
@Data
public class MoocItemSingleCreateDTO {

    /**
     * 慕课id
     */
    @NotNull(message = "慕课id不能为空")
    private Long moocId;

    @NotNull(message = "慕课Item不能为空")
    private MoocItemCreateParam.Item item;
}
