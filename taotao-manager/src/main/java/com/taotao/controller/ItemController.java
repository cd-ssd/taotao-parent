package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.taotao.pojo.Item;
import com.taotao.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class ItemController {

    @Reference
    private ItemService itemService;

    @RequestMapping(value = "/rest/item" , method = RequestMethod.POST)
    @ResponseBody
    public String addItem(Item item , String desc){
        int result =  itemService.addItem(item , desc);
        System.out.println("result===" + result);
        return "success";
    }

    @RequestMapping(value = "/rest/item" , method = RequestMethod.GET)
    @ResponseBody
    public  Map<String  , Object> list(int page , int rows){
        PageInfo<Item> pageInfo = itemService.list(page, rows);
        Map<String  , Object> map = new HashMap<>();
        map.put("total" , pageInfo.getTotal());
        map.put("rows" , pageInfo.getList());
        return map;
    }


}
