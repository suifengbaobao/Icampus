package com.icampus.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icampus.manage.mapper.CartMapper;
import com.icampus.manage.pojo.Cart;
import com.icampus.manage.pojo.Item;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ItemService itemService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 新增一条记录到购物车中
     * 
     * @param json
     * @return
     */
    public boolean save(String json) {
        Cart record = null;
        try {
            // 反序列化获取Cart对象
            record = MAPPER.readValue(json, Cart.class);
            if(record == null){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        Integer count = 0;// 数据操作返回的操作数量
        Long itemId = record.getItemId();// 获取商品id
        Cart cart = this.cartMapper.selectOne(record);// 查询数据，即购物车中是否已有该数据
        if(cart != null){
            // 当购物车中存在该商品，则数目加1
            cart.setNum(cart.getNum() + 1);
            count = this.cartMapper.updateByPrimaryKey(cart);
        }else{
            // 购物车中无该商品，则添加一条记录
            Item item = this.itemService.queryById(itemId);
            // 从本地线程中获取userId
            record.setItemImage(item.getImage());
            record.setItemTitle(item.getTitle());
            record.setItemPrice(item.getPrice());
            record.setCreated(new Date());
            record.setUpdated(record.getCreated());
            record.setNum(1);// 默认每次加一条记录到购物车中
            count = this.cartMapper.insert(record);
        }
        return count.intValue() == 1;
    }
}