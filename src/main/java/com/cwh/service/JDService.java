package com.cwh.service;

import com.cwh.dao.JDResportory;
import com.cwh.pojo.Context;
import com.cwh.utils.HtmlParseUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: es-demo-jd
 * @description:
 * @author: cuiweihua
 * @create: 2020-08-05 17:46
 */
@Service
public class JDService {

    @Resource
    private JDResportory jdResportory;

    /**
     * 获取京东数据存入es
     * @param keyword
     * @return
     * @throws Exception
     */
    public Boolean parseContext(String keyword) throws Exception {
        jdResportory.saveAll( HtmlParseUtils.JDParse(keyword));
        return true;
    }

    public List<Context> searchParams(String keyword, Integer from, Integer size){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title",keyword)));
        builder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        builder.withPageable(PageRequest.of(Integer.parseInt(String.valueOf(from)),Integer.parseInt(String.valueOf(size))));
        Page<Context> page = jdResportory.search(builder.build());
        return  page.getContent();
    }
}
