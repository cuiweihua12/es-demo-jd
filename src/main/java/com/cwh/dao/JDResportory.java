package com.cwh.dao;

import com.cwh.pojo.Context;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @program: es-demo-jd
 * @description:
 * @author: cuiweihua
 * @create: 2020-08-05 17:47
 */
public interface JDResportory extends ElasticsearchRepository<Context, String> {
}
