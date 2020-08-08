package com.cwh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigInteger;

/**
 * @program: es-demo-jd
 * @description:
 * @author: cuiweihua
 * @create: 2020-08-05 17:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName = "context",shards = 1,replicas = 0)
public class Context {

    @Id
    private String id;

    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private String price;

    @Field(type = FieldType.Keyword)
    private String img;

    @Field(type = FieldType.Text)
    private String shop;
}
