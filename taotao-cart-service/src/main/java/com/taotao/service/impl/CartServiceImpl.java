package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Cart;
import com.taotao.pojo.Item;
import com.taotao.service.CartService;
import com.taotao.service.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.service.impl
 *  @文件名:   CartServiceImpl
 *  @创建者:   Chen
 *  @创建时间:  2018/12/18 20:11
 *  @描述：    TODO
 */
@Service
public class CartServiceImpl implements CartService {

    private static final String CART_KEY="ttcart_";
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisTemplate<String,String>redisTemplate;

    @Override
    public void addItemToCart(long userId, long id, int num) {
        //1.查询
        List<Cart>cartList=queryCartByUserId(userId);
        //System.out.println(cartList);
        //2.遍历
        Cart c=null;

        for(Cart cart:cartList){
            if(cart.getItemId()==id){
                c=cart;
                break;
            }
        }
        //判重
        if(c!=null){
            c.setNum(c.getNum()+num);
            c.setUpdate(new Date());
        }else {
            Item item= itemMapper.selectByPrimaryKey(id);
            Cart cart=new Cart();
            cart.setItemId(id);
            cart.setItemTitle(item.getTitle());
            cart.setItemImage(item.getImages()[0]);
            cart.setItemPrice(item.getPrice());
            cart.setCreate(new Date());
            cart.setUpdate(new Date());
            cart.setUserId(userId);
            cart.setNum(num);
            //加进list
            cartList.add(cart);
        }
        String json=new Gson().toJson(cartList);
        System.out.println("购物车商品有" + json);
        redisTemplate.opsForValue().set(CART_KEY+userId,json);
    }

    @Override
    public List<Cart> queryCartByUserId(long userId) {
        String json=redisTemplate.opsForValue().get(CART_KEY+userId);
        System.out.println("json"+json);
        if(!StringUtils.isEmpty(json)){
            List<Cart>cartList=new Gson().fromJson(json,new TypeToken<List<Cart>>(){}.getType());
            return cartList;
        }
        return new ArrayList<Cart>();
    }

    @Override
    public void updateNumByCart(long userId, long itemId, int num) {
        //1.获取购物车
        String json=redisTemplate.opsForValue().get(CART_KEY+userId);
        //2.转化json
        List<Cart>cartList=new Gson().fromJson(json,new TypeToken<List<Cart>>(){}.getType());
        //3.遍历
        for(Cart cart:cartList){
            if(itemId==cart.getItemId()){
                cart.setNum(num);
                cart.setUpdate(new Date());
                break;
            }
        }
        //4.redis
        json=new Gson().toJson(cartList);
        redisTemplate.opsForValue().set(CART_KEY+userId,json);
    }

    @Override
    public void deleteItemByCart(long userId, long itemId) {
        List<Cart> cartList = RedisUtil.findCartFromRedis(redisTemplate, CART_KEY + userId);
        //3.删除
        for(Cart cart:cartList){
            if(itemId==cart.getItemId()){
                cartList.remove(cart);
                break;
            }
        }
        RedisUtil.saveCartToRedis(cartList,redisTemplate,CART_KEY+userId);
    }




}
