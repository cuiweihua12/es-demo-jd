package com.cwh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: es-demo-jd
 * @description:
 * @author: cuiweihua
 * @create: 2020-08-05 16:21
 */
@Controller
public class IndexController {

    @GetMapping({"/","/index"})
    public String index(){
        System.out.println("index");
        return "index";
    }

    @GetMapping("tow")
    public String tow(){
        System.out.println("two");
        return "tow";
    }
}
