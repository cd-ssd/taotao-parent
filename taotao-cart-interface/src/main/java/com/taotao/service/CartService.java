package com.taotao.service;

import com.taotao.pojo.Cart;

import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.service
 *  @文件名:   CartService
 *  @创建者:   Chen
 *  @创建时间:  2018/12/18 20:07
 *  @描述：    TODO
 */
public interface CartService {
    void addItemToCart(long userId,long id,int num);
    List<Cart>queryCartByUserId(long userId);
    void updateNumByCart(long userId,long itemId,int num);
    void deleteItemByCart(long userId,long itemId);

    //void mergeCart(RedisTemplate<String,String> template, long userId);
}
