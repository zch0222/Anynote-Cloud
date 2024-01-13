package com.anynote.common.elasticsearch.model.bo;

import com.anynote.common.elasticsearch.model.vo.SearchVO;
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
    private List<SearchVO<T>> rows;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 1是所有结果，0还有更多结果
     */
    private Integer exactResult;

}
