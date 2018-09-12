package com.taotao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao.service
 *  @文件名:   ServiceApp
 *  @创建者:   Chen
 *  @创建时间:  2018/9/11 14:22
 *  @描述：    TODO
 */
//先开ServiceApp服务类，再开ManagerApp消费类
@SpringBootApplication
public class ServiceApp {
    public static void main(String[]args){

        SpringApplication.run(ServiceApp.class,args);
    }
}
