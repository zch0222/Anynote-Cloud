package com.anynote.core.web.model.bo;

import lombok.Data;

import java.util.List;

/**
 * 分页Bean
 * @author 称霸幼儿园
 */
@Data
public class PageBean<T> {
    private static final long serialVersionUID = 1L;

    private List<T> rows;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer pages;
}
