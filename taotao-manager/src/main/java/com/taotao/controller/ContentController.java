
package com.taotao.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.taotao.pojo.Content;
import com.taotao.service.ContentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;



@RestController
public class ContentController {

    @Reference
    private ContentService contentService;

    @PostMapping("/rest/content")
    public String add(Content content){
        return "success";
    }

    @GetMapping("/rest/content")
    public  Map<String , Object> list(int categoryId , int page , int rows){

        PageInfo<Content> pageInfo = contentService.list(categoryId, page, rows);
        Map<String , Object> map = new HashMap<>();
        map.put("total" , pageInfo.getTotal());
        map.put("rows" , pageInfo.getList());

        return  map;
    }


    @RequestMapping("/rest/content/edit")
    public Map<String ,Integer> edit(Content content){

        int result = contentService.edit(content);

        Map<String ,Integer> map = new HashMap<>();
        if(result > 0 ){
            map.put("status",200);
        }else{
            map.put("status" , 500);
        }

        return map;
    }

    @RequestMapping("/rest/content/delete")
    public  Map<String ,Integer> delete(String ids){

        System.out.println("ids=" + ids);


        int result =  contentService.delete(ids);

        System.out.println("result=" + result);

        Map<String ,Integer> map = new HashMap<>();
        if(result > 0 ){
            map.put("status" , 200);
        }else{
            map.put("status",500);
        }
        return map;
    }

}
