package com.icampus.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.icampus.manage.service.CartService;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    /**
     * 增加一条记录到购物车中
     * @param cart
     * @return
     */
    @RequestMapping(value = "cart/save", method = RequestMethod.POST)
    public ResponseEntity<Void> save(@RequestBody String json){
        try {
            boolean bool = this.cartService.save(json);
            if(bool){
                // 新增成功,响应201
                return ResponseEntity.status(HttpStatus.CREATED).build();
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
