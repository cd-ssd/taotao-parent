package com.taotao;

import com.google.gson.Gson;
import com.taotao.pojo.User;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Date;

/*
 *  @项目名：  taotao-parent 
 *  @包名：    com.taotao
 *  @文件名:   SSoTest
 *  @创建者:   Chen
 *  @创建时间:  2018/11/6 15:55
 *  @描述：    TODO
 */
public class SSoTest {
    @Test
    public void testTicket(){
        User user=new User();
        user.setId(12L);
        user.setEmail("1@aa.cc");
        user.setPhone("10086");
        user.setUsername("zhangsansd");
        user.setPassword("123456");
        user.setCreated(new Date());
        user.setUpdated(new Date());

        String json=new Gson().toJson(user);
        Jedis jedis=new Jedis("192.168.227.129",7002);
        jedis.set("iit_abc",json);
        jedis.close();
    }
}
