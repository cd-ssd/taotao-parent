package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.ItemDescMapper;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;

    @Override
    public int addItem(Item item, String desc) {
        long id = (long) (System.currentTimeMillis() + Math.random() * 10000);
        item.setId(id);
        item.setStatus(1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        int result = itemMapper.insertSelective(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insertSelective(itemDesc);
        return result;
    }

    @Override
    public PageInfo<Item> list(int page, int rows) {
        PageHelper.startPage(page , rows);
        List<Item> list = itemMapper.select(null);
        return new PageInfo<Item>(list);
    }

    @Override
    public Item getItemById(long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteItem(long id) {
        return itemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateItem(Item item) {
        return itemMapper.updateByPrimaryKeySelective(item);
    }
}
