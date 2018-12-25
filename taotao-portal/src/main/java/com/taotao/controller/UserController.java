package com.taotao.controller;

/*
 *  @项目名：  taotao-parent
 *  @包名：    com.itheima.controller
 *  @文件名:   UserController
 *  @创建者:   xiaomi
 *  @创建时间:  2018/10/31 8:42
 *  @描述：    用户的注册
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.cart.CartMergeService;
import com.taotao.pojo.User;
import com.taotao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Reference
    private UserService userService;

    @Autowired
    private CartMergeService cartMergeService;


    @PostMapping("/user/doRegister.shtml")
    @ResponseBody
    public  Map<String  ,String> register(User user){
        System.out.println("user=" + user);
        int result = userService.addUser(user);
        Map<String  ,String> map = new HashMap<>();
        if(result > 0 ){
            map.put("status","200");
        }else{
            map.put("status","500");
        }
        return map;
    }

    @PostMapping("/user/doLogin.shtml")
    @ResponseBody
    public  Map<String ,String> login(User user , HttpServletRequest request, HttpServletResponse response){

        Map<String ,String> map = new HashMap<>();

        String ticket = userService.login(user);

        if(!StringUtils.isEmpty(ticket)){
            System.out.println(ticket);
            cartMergeService.mergeCart(ticket,request,response);

            Cookie cookie = new Cookie("ticket",ticket);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");
            response.addCookie(cookie);
            map.put("status","200");
            map.put("success","http://www.taotao.com");
            return map;
        }

        map.put("status","500");
        return map;
    }
}
