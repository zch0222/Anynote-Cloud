package com.anynote.common.elasticsearch.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author 称霸幼儿园
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchVO<T> {
    /**
     * 高亮信息
     */
    private Map<String, List<String>> highlight;

    /**
     * hit 信息
     */
    private T source;
}
