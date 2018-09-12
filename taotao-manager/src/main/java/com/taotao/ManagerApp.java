package com.taotao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//先开ServiceApp服务类，再开ManagerApp消费类
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class ManagerApp {
    public static void main(String[]args){
        SpringApplication.run(ManagerApp.class,args);
    }
}
