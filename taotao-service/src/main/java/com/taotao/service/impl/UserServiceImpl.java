package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.taotao.mapper.UserMapper;
import com.taotao.pojo.User;
import com.taotao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.itheima.service.impl
 *  @文件名:   UserServiceImpl
 *  @创建者:   xiaomi
 *  @创建时间:  2018/10/31 8:57
 *  @描述：    TODO
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean check(String param, int type) {
        return null;
    }

    @Override
    public String selectUser(String ticket) {
        return null;
    }

    @Override
    public int addUser(User user) {

        user.setCreated(new Date());
        user.setUpdated(new Date());
        //使用MD5进行密码加密
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        return userMapper.insert(user);
    }

    @Override
    public String login(User user) {

        //先对用户的密码进行加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        //根据用户名和密码查询用户。
        List<User> list = userMapper.select(user);
        //如果有，集合里面只会有一个用户，因为用户名是唯一的。
        if(list.size() > 0 ){
            //已经把用户的信息给查询出来了，下一步要做的工作是： 把用户的信息保存在redis里面。
            User loginUser = list.get(0);
            String json = new Gson().toJson(loginUser);
            String key = "tt_"+UUID.randomUUID().toString();
            //把用户的数据保存到redis里面去。
            redisTemplate.opsForValue().set(key, json);
            //redisTemplate.opsForValue().set(key, json,7, TimeUnit.HOURS);

            return key;
        }
        return null;
    }

    public static void main(String [] args){

        //md5 不可逆： 从密文 无法转化出来明文 12306_\ aa123456_
        String s = "123";
        for (int i = 0; i <10 ; i++) {
           s = DigestUtils.md5DigestAsHex(s.getBytes());
        }
        System.out.println("s-=" + s);

    }
}
