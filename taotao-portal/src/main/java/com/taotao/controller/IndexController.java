package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.taotao.pojo.User;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private RedisTemplate<String,String>redisTemplate;

    @Reference
    private ContentService contentService;

    @RequestMapping("/page/{pageName}")
    public String page(@PathVariable String pageName){
        return pageName;
    }

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request){

        Cookie[]cookies=request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                String name=cookie.getName();
                System.out.println("name="+name);
                if("ticket".equals(name)){
                    String key=cookie.getValue();

                    String userInfo=redisTemplate.opsForValue().get(key);
                    User user= new Gson().fromJson(userInfo, User.class);

                    model.addAttribute("user",user);
                    break;
                }
            }
        }
        int categoryId = 89;
        String json = contentService.selectByCategoryId(categoryId);
        System.out.println("json=" + json);
        model.addAttribute("list" , json);
        return "index";
    }
}
