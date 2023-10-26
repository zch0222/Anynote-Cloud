package com.anynote.core.web.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页Bean
 * @author 称霸幼儿园
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> rows;

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
}
