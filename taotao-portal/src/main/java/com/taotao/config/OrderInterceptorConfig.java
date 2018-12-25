package com.taotao.config;

import com.taotao.interceptor.OrderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.config
 *  @文件名:   OrderInterceptorAppConfig
 *  @创建者:   Chen
 *  @创建时间:  2018/12/21 21:35
 *  @描述：    TODO
 */
@Component
public class OrderInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private OrderInterceptor orderInterceptor;
    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry){
      registry.addInterceptor(orderInterceptor).addPathPatterns("/order/**");
    }

}
