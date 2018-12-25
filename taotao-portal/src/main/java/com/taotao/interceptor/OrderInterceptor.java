package com.taotao.interceptor;

import com.taotao.pojo.User;
import com.taotao.utils.CookieUtil;
import com.taotao.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.interceptor
 *  @文件名:   OrderInterceptor
 *  @创建者:   Chen
 *  @创建时间:  2018/12/21 21:47
 *  @描述：    TODO
 */
@Component
public class OrderInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String>template;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ticket= CookieUtil.findTicket(request);
        if(StringUtils.isEmpty(ticket)){
            System.out.println("未登陆，拦截");

            String uri=request.getRequestURI();

            response.sendRedirect("/page/login.shtml?url="+uri);
            return false;
        }
        //双重判定用户登录
        User user = RedisUtil.findUserByTicket(template, ticket);
        if(user==null){
            System.out.println("登陆失效，拦截");
            response.sendRedirect("/page/login.shtml");
            return false;
        }

        request.setAttribute("user",user);
        System.out.println("已登录");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
