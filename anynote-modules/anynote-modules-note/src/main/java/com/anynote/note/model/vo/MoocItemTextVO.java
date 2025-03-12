package com.anynote.note.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoocItemTextVO {

    /**
     * 慕课item文本id
     */
    private Long id;

    /**
     * 慕课item id
     */
    private Long moocItemId;

    /**
     * 慕课item文本内容
     */
    private String itemText;

    /** 创建者 */
    private Long createBy;

    /** 创建时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
}
