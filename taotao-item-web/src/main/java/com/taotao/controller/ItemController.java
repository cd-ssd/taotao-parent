package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.pojo.Item;
import com.taotao.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.controller
 *  @文件名:   ItemController
 *  @创建者:   Chen
 *  @创建时间:  2018/12/5 19:11
 *  @描述：    TODO
 */
@Controller
public class ItemController {

    @Reference
    private ItemService itemService;
    @RequestMapping("item/{id}")
    public String item(@PathVariable long id,Model model){
        Item item=itemService.getItemById(id);
        model.addAttribute("item",item);
        return "item";
    }
}
