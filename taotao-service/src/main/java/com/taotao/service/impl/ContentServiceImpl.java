package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.taotao.mapper.ContentMapper;
import com.taotao.pojo.Content;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Override
    public int add(Content content) {
        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        int result = contentMapper.insert(content);
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("bingAd","");
        return result;
    }

    @Override
    public PageInfo<Content> list(int categoryId, int page, int rows) {

        PageHelper.startPage(page, rows);
        Content content = new Content();
        content.setCategoryId((long) categoryId);
        List<Content> list = contentMapper.select(content);
        return new PageInfo<>(list);
    }

    @Override
    public int edit(Content content) {
        content.setUpdated(new Date());
        int result = contentMapper.updateByPrimaryKeySelective(content);
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("bingAd","");
        return result;
    }

    @Override
    public int delete(String ids) {
        String[] idArray = ids.split(",");
        int result = 0 ;
        for(String id : idArray){
            result += contentMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("bingAd","");
        return result;
    }

    @Override
    public String selectByCategoryId(long cid) {
        System.out.println("============");
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String json = operations.get("bingAd");
        System.out.println("从缓存里面获取广告数据:" + json);
        if(!StringUtils.isEmpty(json)){ //false ： 非空。
            System.out.println("缓存里面有广告的数据，直接返回");
            return json;
        }
        System.out.println("缓存里面没有有广告的数据，要去查询数据库。");
        Content c = new Content();
        c.setCategoryId(cid);
        List<Content> contents = contentMapper.select(c);
        List<Map<String , Object>> list = new ArrayList<>();
        for(Content content :contents){
            Map<String , Object> map = new HashMap<>();
            map.put("src" , content.getPic());
            map.put("width",670);
            map.put("height" , 240);
            map.put("href",content.getUrl());

            list.add(map);
        }
        json = new Gson().toJson(list);
        operations.set( "bingAd", json);
        System.out.println("从mysql查询出来的数据要存到redis数据库去");
        return json;
    }
}
