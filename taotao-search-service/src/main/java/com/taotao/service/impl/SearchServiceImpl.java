package com.taotao.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.pojo.Item;
import com.taotao.pojo.Page;
import com.taotao.service.SearchService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public Page<Item> searchItem(String q, int page) {
        try {

            int pageSize = 16;
            SolrQuery query = new SolrQuery();
            query.setQuery("item_title:"+q);
            query.setStart((page - 1 ) * pageSize);
            query.setRows(pageSize);
            query.setHighlight(true);
            query.addHighlightField("item_title");
            query.setHighlightSimplePre("<font color='red'>");
            query.setHighlightSimplePost("</font>");
            QueryResponse response = solrClient.query(query);
            SolrDocumentList results = response.getResults();

            long total = results.getNumFound();
            System.out.println("total=:"+total);

            Map<String, Map<String, List<String>>> map = response.getHighlighting();
            List<Item> itemList = new ArrayList<Item>();

            for (SolrDocument document : results) {

                long id = Long.parseLong((String) document.getFieldValue("id"));
                String title = (String) document.getFieldValue("item_title");
                String image = (String) document.getFieldValue("item_image");
                long price = (long) document.getFieldValue("item_price");
                long cid = (long) document.getFieldValue("item_cid");
                Item item = new Item();
                item.setId(id);
                item.setCid(cid);

                List<String> list = map.get(id + "").get("item_title");
                if(list != null && list.size() > 0 ){
                    String hlTitle = list.get(0);
                    item.setTitle(hlTitle);
                }else{

                    item.setTitle(title);
                }
                item.setPrice(price);
                item.setImage(image);
                //System.out.println("item=" + item);
                itemList.add(item);
            }
            Page<Item> pageItem = new Page<Item>(total ,page , pageSize);
            pageItem.setList(itemList);

            return pageItem;

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
