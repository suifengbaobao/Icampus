package com.icampus.manage.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.icampus.manage.pojo.Item;
import com.icampus.manage.service.ItemService;

@Controller
@RequestMapping("api")
public class ApiItemController {
    @Autowired
    private ItemService itemService;
    /**
     * 查询商品集合
     * @return
     */
    @RequestMapping(value = "item", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> queryList(){
        try {
            List<Item> itemList = this.itemService.queryList();
            if(itemList != null){
                return ResponseEntity.ok(itemList);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
