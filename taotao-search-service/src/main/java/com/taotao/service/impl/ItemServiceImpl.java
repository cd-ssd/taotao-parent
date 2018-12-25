package com.taotao.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.mapper.ItemMapper;
import com.taotao.pojo.Item;
import com.taotao.service.ItemService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrClient solrClient;


    //收到的消息其实就是商品的id值。
    @JmsListener(destination = "item-save")
    @Override
    public void addItem(String message) {

        try {
            System.out.println("收到的 message=" + message);

            //根据id查询商品数据
            long id = Long.parseLong(message);
            Item item = itemMapper.selectByPrimaryKey(id);

            // 2. 把商品数据添加到索引库。

            SolrInputDocument doc  =new SolrInputDocument();

            doc.addField("id" , item.getId());
            doc.addField("item_title" , item.getTitle());
            doc.addField("item_image" ,item.getImage());
            doc.addField("item_price" , item.getPrice());
            doc.addField("item_cid" , item.getCid());
            doc.addField("item_status" , item.getStatus());

            UpdateResponse response=solrClient.add(doc);
            System.out.println("aaa"+response);
            solrClient.commit();

            System.out.println("更新索引库成功");

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
