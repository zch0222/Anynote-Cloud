package com.anynote.note.model.dto;

import com.anynote.note.model.bo.MoocItemCreateParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MoocItemCreateDTO {

    /**
     * 慕课id
     */
    @NotNull(message = "慕课id不能为空")
    private Long moocId;

    /**
     * 知识库id
     */
    @NotNull(message = "知识库id")
    private Long knowledgeBaseId;

    @NotNull(message = "慕课Item不能为空")
    @NotEmpty(message = "慕课Item不能为空")
    private List<MoocItemCreateParam.Item> items;
}
