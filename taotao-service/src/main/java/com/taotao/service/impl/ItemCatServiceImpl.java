package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.mapper.ItemCatMapper;
import com.taotao.pojo.ItemCat;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> selectItemCatByParentId(long parentId) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parentId);
        return itemCatMapper.select(itemCat);

    }
}
