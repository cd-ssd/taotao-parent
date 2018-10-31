package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.service.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Reference
    private ContentService contentService;

    @RequestMapping("/page/{pageName}")
    public String page(@PathVariable String pageName){
        return pageName;
    }

    @RequestMapping("/")
    public String index(Model model){
        int categoryId = 89;
        String json = contentService.selectByCategoryId(categoryId);
        System.out.println("json=" + json);
        model.addAttribute("list" , json);
        return "index";
    }
}
