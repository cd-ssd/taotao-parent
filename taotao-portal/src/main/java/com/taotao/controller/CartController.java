package com.taotao.controller;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.controller
 *  @文件名:   CartController
 *  @创建者:   Chen
 *  @创建时间:  2018/12/18 19:56
 *  @描述：    TODO
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.cart.CartCookieService;
import com.taotao.pojo.Cart;
import com.taotao.pojo.User;
import com.taotao.service.CartService;
import com.taotao.utils.CookieUtil;
import com.taotao.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class CartController {

    @Reference
    private CartService cartService;

    @Autowired
    private RedisTemplate<String,String>template;

    @Autowired
    private CartCookieService cartCookieService;
    //@ResponseBody
    @RequestMapping("/cart/add/{id}.html")
    public String addToCart(@PathVariable long id, int num, HttpServletRequest request, HttpServletResponse response){

        String ticket= CookieUtil.findTicket(request);

        if(ticket!=null){
            User user=RedisUtil.findUserByTicket(template,ticket);
            cartService.addItemToCart(user.getId(),id,num);
        }else{
            System.out.println("未登录添加商品到购物车");
            cartCookieService.addItemByCookie(id,num,request,response);
        }
        return "cartSuccess";
    }

    @RequestMapping("/cart/cart.html")
    public String showCart(HttpServletRequest request,Model model) throws UnsupportedEncodingException {
        //1.用户
        String ticket=CookieUtil.findTicket(request);
        List<Cart>cartList=null;
        //2.redis
        if(ticket!=null){
            User user=RedisUtil.findUserByTicket(template,ticket);
            cartList= cartService.queryCartByUserId(user.getId());
            //model.addAttribute("cartList",cartList);
        }else{
            //未登录购物车
           cartList=cartCookieService.queryCartByCookie(request);
        }

        model.addAttribute("cartList",cartList);

        return "cart";
    }

    @RequestMapping("/service/cart/update/num/{id}/{num}")
    @ResponseBody
    public void updateNumByCart(@PathVariable long id, @PathVariable int num, HttpServletRequest request,HttpServletResponse response){
        String ticket=CookieUtil.findTicket(request);
        if(ticket!=null){
            User user=RedisUtil.findUserByTicket(template,ticket);
            cartService.updateNumByCart(user.getId(),id,num);
        }else{
            cartCookieService.updateCartByCookie(id, num, request, response);
        }
    }

    @RequestMapping("/cart/delete/{id}.shtml")
    public String deleteItemByCart(@PathVariable long id,HttpServletRequest request,HttpServletResponse response){
        String ticket=CookieUtil.findTicket(request);

        if(ticket!=null){
            User user=RedisUtil.findUserByTicket(template,ticket);
            cartService.deleteItemByCart(user.getId(),id);
        }else {
            cartCookieService.deleteCartByCookie(id, request, response);
        }


        return "redirect:/cart/cart.html";
    }
}
