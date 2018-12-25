package com.taotao.cart;

import com.taotao.pojo.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.service.impl
 *  @文件名:   CartCookieService
 *  @创建者:   Chen
 *  @创建时间:  2018/12/19 11:27
 *  @描述：    TODO
 */
public interface CartCookieService {
    void addItemByCookie(long itemId, int num, HttpServletRequest request, HttpServletResponse  response);
    List<Cart> queryCartByCookie(HttpServletRequest request);
    void updateCartByCookie(long itemId, int num,HttpServletRequest request,HttpServletResponse response);
    void deleteCartByCookie(long itemId,HttpServletRequest request,HttpServletResponse response);
}
