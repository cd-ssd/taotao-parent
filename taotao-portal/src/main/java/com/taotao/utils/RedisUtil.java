package com.taotao.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taotao.pojo.Cart;
import com.taotao.pojo.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.utils
 *  @文件名:   RedisUtil
 *  @创建者:   Chen
 *  @创建时间:  2018/12/18 20:54
 *  @描述：    TODO
 */
public class RedisUtil {
    public static User findUserByTicket(RedisTemplate template,String ticket){
        String json= (String) template.opsForValue().get(ticket);

        User user=null;
        if(!StringUtils.isEmpty(json)){
            user=new Gson().fromJson(json,User.class);
        }
        return user;
    }
    public static List<Cart> findCartFormRedis(RedisTemplate<String,String>template, String key){
        //1.Redis购物车
        String json = template.opsForValue().get(key);

        List<Cart> redisList=new Gson().fromJson(json,new TypeToken<List<Cart>>(){}.getType());

        return redisList;
    }
}
