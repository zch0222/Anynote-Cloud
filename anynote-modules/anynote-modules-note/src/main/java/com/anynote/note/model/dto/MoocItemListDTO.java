package com.anynote.note.model.dto;

import com.anynote.core.web.model.dto.PageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocItemListDTO extends PageDTO {

    /**
     * item 父节点id
     */
    @NotNull(message = "父节点id不能为空")
    private Long parentId;

    /**
     * 慕课id
     */
    @NotNull(message = "慕课id不能为空")
    private Long moocId;

    /**
     * 慕课类型对象 0.章节 1.视频 2.文档
     */
    private Integer moocItemType;
}
