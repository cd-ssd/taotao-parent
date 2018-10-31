package com.taotao.service;

import com.github.pagehelper.PageInfo;
import com.taotao.pojo.Item;

public interface ItemService {

    int addItem(Item item, String desc);
    PageInfo<Item> list(int page , int rows);
    Item getItemById(long id);
    int deleteItem(long id);
    int updateItem(Item item);
}
