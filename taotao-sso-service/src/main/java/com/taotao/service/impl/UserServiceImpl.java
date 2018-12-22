package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.mapper.UserMapper;
import com.taotao.pojo.User;
import com.taotao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/*
 *  @项目名：  taotao-parent
 *  @包名：    com.taotao.service.impl
 *  @文件名:   UserServiceImpl
 *  @创建者:   chen
 *  @创建时间:  2018/10/24 18:47
 *  @描述：    TODO
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    /**
     * 检查给定的参数是否存在
     * @param param
     * @param type
     * @return true:表示存在，false:表示不存在
     */
    @Override
    public Boolean check(String param, int type) {

        User user = new User();

        switch (type) {
            case 1: //用户名
                user.setUsername(param);
                break;
            case 2: //电话
                user.setPhone(param);
                break;
            case 3: //邮箱
                user.setEmail(param);
                break;
            default:
                user.setUsername(param);
                break;
        }

        List<User> list = userMapper.select(user);

        //false : 表示不能用了，已经被占用
        //true : 表示可以使用。

        return list.size()>0?false:true ;

        // return list.size() <= 0 ;
    }

    @Override
    public String selectUser(String ticket) {

        String key = "tt_"+ticket;

        //这里要从redis里面获取用户的信息
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public int addUser(User user) {
        return 0;
    }

    @Override
    public String login(User user) {
        return null;
    }


}
