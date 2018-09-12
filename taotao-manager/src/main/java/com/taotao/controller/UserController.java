package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao
 *  @文件名:   UserController
 *  @创建者:   Chen
 *  @创建时间:  2018/9/11 11:03
 *  @描述：    TODO
 */
@RestController
public class UserController {

    @Reference  //注意： 这里使用的是dubbo的注解
    private UserService userService;

    @RequestMapping("save")
    public String save(){
        System.out.println("save~!");
        userService.save();
        return "save success~!";
    }


}
