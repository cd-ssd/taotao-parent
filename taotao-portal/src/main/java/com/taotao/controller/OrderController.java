package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.pojo.Cart;
import com.taotao.pojo.Order;
import com.taotao.pojo.User;
import com.taotao.service.CartService;
import com.taotao.service.OrderService;
import com.taotao.utils.CookieUtil;
import com.taotao.utils.RedisUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.controller
 *  @文件名:   OrderController
 *  @创建者:   Chen
 *  @创建时间:  2018/12/20 22:07
 *  @描述：    TODO
 */
@Controller
public class OrderController {
    @Autowired
    private RedisTemplate<String,String>template;

    @Reference
    private OrderService orderService;

    @Reference
    private CartService cartService;
    @RequestMapping("/order/order-cart.shtml")
    public String create(HttpServletRequest request,Model model){

        //获取user
        User user=(User)request.getAttribute("user");

        List<Cart> carts = cartService.queryCartByUserId(user.getId());

        //model
        model.addAttribute("carts",carts);

        return "order-cart";
    }

    @RequestMapping("/service/order/submit")
    @ResponseBody
    public Map<String,Object> submitOrder(Order order,HttpServletRequest request){
        //1.设置下单用户，谁提交订单
        String ticket= CookieUtil.findTicket(request);
        User user= RedisUtil.findUserByTicket(template,ticket);


        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());

        //2.执行订单添加操作，返回订单id
        String orderId=orderService.saveOrder(order);


        //3.封装
        Map<String,Object> map=new HashMap<>();
        map.put("status",200);
        map.put("data",orderId);
        System.out.println("order"+order);
        return map;
    }

    @RequestMapping("order/success.html")
    public String showOrder(String id,Model model){
        //id查订单对象
        Order order=orderService.queryOrderByOrderId(id);
        model.addAttribute("order",order);

        String date=new DateTime().plusDays(2).toString("yyyy年MM月dd日HH时mm分ss秒SSS毫秒");
        model.addAttribute("date",date);

        return "success";
    }
}
