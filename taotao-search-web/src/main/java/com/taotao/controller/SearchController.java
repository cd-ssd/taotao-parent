package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.pojo.Item;
import com.taotao.pojo.Page;
import com.taotao.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Reference
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(Model model, String q , @RequestParam(defaultValue = "1") int page){
        Page<Item>PageItem=searchService.searchItem(q,page);
        model.addAttribute("query",q);
        model.addAttribute("page",PageItem);
        model.addAttribute("totalPages",PageItem.getRpage());
        //model.addAttribute("page",page);
        return "search";
    }
}
