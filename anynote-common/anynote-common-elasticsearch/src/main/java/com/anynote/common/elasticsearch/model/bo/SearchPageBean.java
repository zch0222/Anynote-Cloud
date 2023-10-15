package com.anynote.common.elasticsearch.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 搜索分页Bean
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPageBean<T> {
    private List<T> rows;

    /**
     * 条数
     */
    private Long count;

    /**
     * 0是所有结果，1还有更多结果
     */
    private Integer exactResult;

}
