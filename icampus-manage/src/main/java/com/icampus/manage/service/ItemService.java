package com.icampus.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.icampus.manage.mapper.ItemMapper;
import com.icampus.manage.pojo.Item;

@Service
public class ItemService {
    @Autowired
    private ItemMapper itemMapper;
    /**
     * 查询所有的商品信息
     * @return
     */
    public List<Item> queryList() {
        Example example = new Example(Item.class);
        // 设置查询的排序规则
        example.setOrderByClause("updated DESC");
        return this.itemMapper.selectByExample(example);
    }
    
    /**
     * 根据商品id查询商品信息
     * @param id
     * @return
     */
    public Item queryById(Long id){
        return this.itemMapper.selectByPrimaryKey(id);
    }

}
