package com.taotao.service;

import com.taotao.pojo.ContentCategory;

import java.util.List;


public interface ContentCategoryService {

    List<ContentCategory>  getCategoryByParentId(Long id);
    ContentCategory add(ContentCategory contentCategory);
    int update(ContentCategory contentCategory);
    int delete(ContentCategory contentCategory);
}
