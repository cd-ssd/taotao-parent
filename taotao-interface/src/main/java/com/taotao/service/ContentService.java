package com.taotao.service;

import com.github.pagehelper.PageInfo;
import com.taotao.pojo.Content;


public interface ContentService {
    int add(Content content);
    PageInfo<Content> list(int categoryId, int page , int rows);
    int edit(Content content);
    int delete(String ids);
    String  selectByCategoryId(long cid);
}
