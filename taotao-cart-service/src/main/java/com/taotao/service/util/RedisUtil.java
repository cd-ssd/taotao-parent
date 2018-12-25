package com.taotao.service.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taotao.pojo.Cart;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.service.util
 *  @文件名:   RedisUtil
 *  @创建者:   Chen
 *  @创建时间:  2018/12/20 16:00
 *  @描述：    TODO
 */
public class RedisUtil {
    public static List<Cart> findCartFromRedis(RedisTemplate<String,String>redisTemplate,String key){
        String json=redisTemplate.opsForValue().get(key);
        List<Cart>cartList=new Gson().fromJson(json,new TypeToken<List<Cart>>(){}.getType());

        return cartList;
    }
    public static void saveCartToRedis(List<Cart>cartList,RedisTemplate<String,String>redisTemplate,String key) {
        String  json=new Gson().toJson(cartList);
        redisTemplate.opsForValue().set(key,json);
    }
}
