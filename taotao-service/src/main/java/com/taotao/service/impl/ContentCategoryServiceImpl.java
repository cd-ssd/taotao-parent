package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.mapper.ContentCategoryMapper;
import com.taotao.pojo.ContentCategory;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper mapper;

    @Override
    public List<ContentCategory> getCategoryByParentId(Long id) {
        ContentCategory category = new ContentCategory();
        category.setParentId(id);
        return mapper.select(category);
    }

    @Override
    public ContentCategory add(ContentCategory contentCategory) {
        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        mapper.insertSelective(contentCategory);

        Long parentId = contentCategory.getParentId();
        ContentCategory parentCategory = mapper.selectByPrimaryKey(parentId);
        if(!parentCategory.getIsParent()){
            parentCategory.setIsParent(true);
        }
        mapper.updateByPrimaryKeySelective(parentCategory);
        System.out.println("contentCate=" + contentCategory);
        return contentCategory;
    }

    @Override
    public int update(ContentCategory contentCategory) {
        int rows = mapper.updateByPrimaryKeySelective(contentCategory);
        System.out.println("rows=" + rows);
        return rows;
    }

    @Override
    public int delete(ContentCategory contentCategory) {
        List<ContentCategory> list = new ArrayList<>();
        list.add(contentCategory);
        findAllChild(list , contentCategory.getId());
        int result =deleteAll(list);
        ContentCategory c = new ContentCategory();
        c.setParentId(contentCategory.getParentId());
        int count = mapper.selectCount(c);
        if(count < 1){
            c = new ContentCategory();
            c.setId(contentCategory.getParentId());
            c.setIsParent(false);
            mapper.updateByPrimaryKeySelective(c);
        }
        return result;
    }

    private int deleteAll(List<ContentCategory> list) {

        int result = 0 ;
        for (ContentCategory category : list) {
            result += mapper.delete(category);
        }
        return result;

    }

    private void findAllChild(List<ContentCategory> list, Long id) {

        List<ContentCategory> childList = getCategoryByParentId(id);
        if(childList !=null && childList.size() > 0 ){
            for (ContentCategory category : childList) {
                list.add(category);
                findAllChild(list , category.getId());
            }
        }
    }
}
