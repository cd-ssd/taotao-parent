package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/rest/page/{pageName}")
    public String page(@PathVariable String pageName){
        System.out.println("pageName=" + pageName);
        return pageName;
    }
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
