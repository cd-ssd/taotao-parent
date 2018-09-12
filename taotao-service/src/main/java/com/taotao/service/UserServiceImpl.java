package com.taotao.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.service
 *  @文件名:   UserServiceImpl
 *  @创建者:   Chen
 *  @创建时间:  2018/9/11 13:38
 *  @描述：    TODO
 */
@Service //注意，这个注解使用的是dubbo的注解，而不是Spring提供的@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void save() {
        System.out.println("调用了UserServiceImpl的save方法~！");
    }






}
