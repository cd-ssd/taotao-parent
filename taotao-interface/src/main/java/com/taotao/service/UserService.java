package com.taotao.service;


import com.taotao.pojo.User;

public interface UserService {

    Boolean check(String param , int type);
    String selectUser(String ticket);

    int addUser(User user);

    String login(User user);
}
