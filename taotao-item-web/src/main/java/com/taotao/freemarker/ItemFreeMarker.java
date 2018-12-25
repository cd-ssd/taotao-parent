package com.taotao.freemarker;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

;

/*
 *  @项目名：  taotao-parent
 *  @包名：    com.taotao.service
 *  @文件名:   ItemService
 *  @创建者:   Chen
 *  @创建时间:  2018/12/11 14:02
 *  @描述：    TODO
 */
@Component
public class ItemFreeMarker {

    @Reference
    private ItemService itemService;

    @Reference
    private ItemDescService itemDescService;

    @JmsListener(destination = "item-save")
    public void addItem(String message) throws Exception {
        System.out.println(itemService+"=="+message);
        System.out.println(itemDescService);
        Item item=itemService.getItemById(Long.parseLong(message));
        ItemDesc itemDesc = itemDescService.getDescById(Long.parseLong(message));


        Configuration configuration=new Configuration(Configuration.VERSION_2_3_27);

        String path="D:\\idea project\\taotao-parent\\taotao-item-web\\src\\main\\webapp\\ftl";

        configuration.setDirectoryForTemplateLoading(new File(path));

        Template template=configuration.getTemplate("item.ftl");
        Writer out=new FileWriter("D:\\taotao\\item\\"+message+".html");

        Map<String,Object>root=new HashMap<>();
        root.put("item",item);
        root.put("itemDesc" , itemDesc);
        template.process(root,out);
        out.close();
    }
}
