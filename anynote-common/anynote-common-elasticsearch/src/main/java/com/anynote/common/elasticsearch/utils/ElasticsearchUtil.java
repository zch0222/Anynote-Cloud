package com.anynote.common.elasticsearch.utils;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import com.anynote.common.elasticsearch.model.EsNoteIndex;
import com.anynote.common.elasticsearch.model.bo.SearchPageBean;
import com.anynote.common.elasticsearch.model.vo.SearchVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 称霸幼儿园
 */
public class ElasticsearchUtil {

    public static <T> SearchPageBean<T> buildSearchPageBean(SearchResponse<T> searchResponse, Class tClass) {
        TotalHits total = searchResponse.hits().total();
        List<Hit<T>> hits = searchResponse.hits().hits();
        List<SearchVO<T>> tList = new ArrayList<>();
        for (Hit<T> hit : hits) {
            tList.add(SearchVO.<T>builder()
                    .highlight(hit.highlight())
                    .source(hit.source())
                    .build());
        }
        int exactResult = total.relation() == TotalHitsRelation.Eq ? 1 : 0;
        return SearchPageBean.<T>builder()
                .rows(tList)
                .total(total.value())
                .exactResult(exactResult)
                .build();
    }
}
