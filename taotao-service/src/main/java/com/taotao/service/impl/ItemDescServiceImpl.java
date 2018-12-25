package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.mapper.ItemDescMapper;
import com.taotao.pojo.ItemDesc;
import com.taotao.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ItemDescServiceImpl implements ItemDescService {

    @Autowired
    private ItemDescMapper itemDescMapper;

    @Override
    public ItemDesc getDescById(long id) {
        return itemDescMapper.selectByPrimaryKey(id);
    }
}
