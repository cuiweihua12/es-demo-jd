package com.cwh.pojo;

import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @program: es-demo-jd
 * @description:
 * @author: cuiweihua
 * @create: 2020-08-05 14:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private String name;
    private Date birthday;
}
