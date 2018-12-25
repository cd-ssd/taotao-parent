package com.taotao.cart.impl;

import com.google.gson.Gson;
import com.taotao.cart.CartCookieService;
import com.taotao.cart.CartMergeService;
import com.taotao.pojo.Cart;
import com.taotao.pojo.User;
import com.taotao.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.cart.impl
 *  @文件名:   CartMergeServiceImpl
 *  @创建者:   Chen
 *  @创建时间:  2018/12/20 19:31
 *  @描述：    TODO
 */
@Service
public class CartMergeServiceImpl implements CartMergeService{

    @Autowired
    private RedisTemplate<String,String> template;

    @Autowired
    private CartCookieService cartCookieService;

    private static final String CART_KEY="ttcart_";

    @Override
    public void mergeCart(String ticket, HttpServletRequest request, HttpServletResponse response) {
        //1.Cookie购物车
        List<Cart> cookieList = cartCookieService.queryCartByCookie(request);
        //2.Redis购物车

        User user=RedisUtil.findUserByTicket(template ,ticket);
        long userId=user.getId();
        System.out.println("userId:"+userId);
        List<Cart> redisList = RedisUtil.findCartFormRedis(template, CART_KEY + userId);
        System.out.println(cookieList);
        //3.遍历,合并
        for(Cart cart:cookieList){
            if(redisList.contains(cart)){
                int index=redisList.indexOf(cart);
                Cart c=redisList.get(index);
                c.setNum(c.getNum()+cart.getNum());
                c.setUpdate(new Date());
            }else{
                redisList.add(cart);
            }
        }
        //4.存到redis
        String json=new Gson().toJson(redisList);
        template.opsForValue().set(CART_KEY+userId,json);
        //5.删除原来的购物车
        Cookie c=new Cookie("tt_cart","");
        c.setMaxAge(0);
        response.addCookie(c);
    }
}
