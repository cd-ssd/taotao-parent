package com.taotao.cart.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taotao.cart.CartCookieService;
import com.taotao.pojo.Cart;
import com.taotao.pojo.Item;
import com.taotao.service.ItemService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.service.impl
 *  @文件名:   CartCookieServiceImpl
 *  @创建者:   Chen
 *  @创建时间:  2018/12/19 11:30
 *  @描述：    TODO
 */
@Service
public class CartCookieServiceImpl implements CartCookieService {

    private final static String CART_KEY="tt_cart";

    @Reference
    private ItemService itemService;

    @Override
    public void addItemByCookie(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {

        //1.从cookie查询购物车
        List<Cart>cartList=queryCartByCookie(request);

        //2.判读是否存在
        Cart c=null;
        for(Cart cart:cartList){
            if(itemId==cart.getItemId()){
                c=cart;
                break;
            }
        }

        if(c!=null){
            //有这个商品
            c.setNum(c.getNum()+num);
        }else {
            //没有这个商品
            Item item= itemService.getItemById(itemId);

            Cart cart=new Cart();
            cart.setItemId(itemId);
            cart.setItemTitle(item.getTitle());
            cart.setItemImage(item.getImages()[0]);
            cart.setItemPrice(item.getPrice());
            cart.setCreate(new Date());
            cart.setUpdate(new Date());
            cart.setNum(num);

            cartList.add(cart);
        }
        String json=new Gson().toJson(cartList);

        try{
            json= URLEncoder.encode(json,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }


        Cookie cookie=new Cookie(CART_KEY,json);
        cookie.setMaxAge(60*60*24*7);

        cookie.setPath("/");

        response.addCookie(cookie);
        System.out.println("添加到cookie缓存");
    }

    //从cookie查询购物车
    @Override
    public List<Cart> queryCartByCookie(HttpServletRequest request){
        //1.获取购物车商品
        List<Cart>cartList=null;
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (CART_KEY.equals(cookie.getName())) {
                        String json = cookie.getValue();
                        json = URLDecoder.decode(json, "utf-8");
                        cartList = new Gson().fromJson(json, new TypeToken<List<Cart>>() {}.getType());
                    }
                }
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        if(cartList==null){
            cartList=new ArrayList<>();
        }
        return cartList;
    }

    @Override
    public void updateCartByCookie(long itemId, int num,HttpServletRequest request,HttpServletResponse response) {

        List<Cart>cartList=queryCartByCookie(request);

        for(Cart cart:cartList){
            if(cart.getItemId()==itemId){
                cart.setNum(num);
                cart.setUpdate(new Date());
                break;
            }
        }
        try {
            String json=new Gson().toJson(cartList);
            json=URLEncoder.encode(json,"utf-8");
            Cookie cookie = new Cookie(CART_KEY,json);
            cookie.setMaxAge(60*60*24*7);
            cookie.setPath("/");
            response.addCookie(cookie);
        }catch (Exception e){

        }
    }

    @Override
    public void deleteCartByCookie(long itemId,HttpServletRequest request,HttpServletResponse response) {
        //1.查车
        List<Cart>cartList=queryCartByCookie(request);
        //2.遍历
        Iterator<Cart>iterator=cartList.iterator();
        while(iterator.hasNext()){
            Cart cart=iterator.next();
            if(itemId==cart.getItemId()){
                iterator.remove();
            }
        }

        try {
            String json=new Gson().toJson(cartList);
            json=URLEncoder.encode(json,"utf-8");
            Cookie cookie=new Cookie(CART_KEY,json);
            cookie.setMaxAge(60*60*24*7);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}
