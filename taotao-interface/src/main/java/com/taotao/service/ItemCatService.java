package com.taotao.service;

import com.taotao.pojo.ItemCat;

import java.util.List;


public interface ItemCatService {

    List<ItemCat> selectItemCatByParentId(long parentId);
}
