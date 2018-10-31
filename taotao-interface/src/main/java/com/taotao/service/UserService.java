package com.taotao.service;


public interface UserService {

    Boolean check(String param , int type);
    String selectUser(String ticket);
}
