package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.pojo.ItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    @RequestMapping("/rest/item/cat")
    @ResponseBody
    public List<ItemCat> selectItemCat(@RequestParam(defaultValue = "0") long id){

        List<ItemCat> list = itemCatService.selectItemCatByParentId(id);

        System.out.println("list==" + list);

        return list;
    }
}
