package com.taotao.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.service.ItemService;
import com.taotao.pojo.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class ItemInterfaceController {

    @Reference
    private ItemService itemService;

    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable long id ){
        try {
            Item item = itemService.getItemById(id);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/item")
    public ResponseEntity<Void> addItem(Item item , String desc){
        try {
            int result = itemService.addItem(item,desc);
            System.out.println("新增结果:" + result);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id){
        try {
            int result = itemService.deleteItem(id);
            System.out.println("删除结果:" + result);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/item")
    public ResponseEntity<Void> deleteItem(Item item){
        try {
            int result = itemService.updateItem(item);
            System.out.println("更新结果:" + result);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
