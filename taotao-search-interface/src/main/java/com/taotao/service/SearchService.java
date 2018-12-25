package com.taotao.service;

import com.taotao.pojo.Item;
import com.taotao.pojo.Page;






public interface SearchService {
    Page<Item> searchItem(String q, int page);
}
