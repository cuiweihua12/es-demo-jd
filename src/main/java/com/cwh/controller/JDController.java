package com.cwh.controller;

import com.cwh.pojo.Context;
import com.cwh.service.JDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: es-demo-jd
 * @description:
 * @author: cuiweihua
 * @create: 2020-08-05 17:56
 */
@Controller
public class JDController {

    @Autowired
    private JDService service;

    @GetMapping("/parse/{keyword}")
    @ResponseBody
    public String  jdParse(@PathVariable("keyword") String keyword) throws Exception {
        service.parseContext(keyword);
        return "aa";
    }

    @GetMapping("/params/{keyword}/{from}/{size}")
    @ResponseBody
    public List<Context> searchParams(@PathVariable("keyword") String keywrod,
                                      @PathVariable("from") Integer from,
                                      @PathVariable("size") Integer size){

        return service.searchParams(keywrod,from,size);
    }
}
