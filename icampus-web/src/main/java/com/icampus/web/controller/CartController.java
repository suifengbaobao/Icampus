package com.icampus.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.icampus.web.service.CartService;




@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    /**
     * 增加一条记录到购物车中
     * @param cart
     * @return
     */
    @RequestMapping(value = "cart/save/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<Void> save(@PathVariable("itemId")Long itemId){
        try {
            boolean bool = this.cartService.save(itemId);
            if(bool){
                // 新增成功,响应200
                return ResponseEntity.ok(null);
            }else{
                // 新增失败，响应500
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 服务器内部错误，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
