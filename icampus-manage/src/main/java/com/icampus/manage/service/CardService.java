package com.icampus.manage.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icampus.common.bean.EasyUIResult;
import com.icampus.manage.mapper.CardMapper;
import com.icampus.manage.pojo.Card;

@Service
public class CardService {
    @Autowired
    private CardMapper cardMapper;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 根据卡片类型查询卡片集合
     * @param type
     * @return
     */
    public EasyUIResult queryCardListByType(String type, String title, Integer page, Integer rows) {
        List<Card> cardList = null;
        Example example = new Example(Card.class);
        // 设置分页信息
        PageHelper.startPage(page, rows);
        // 设置排序条件
        example.setOrderByClause("stars DESC");
        if(StringUtils.isEmpty(title)){
            if(StringUtils.equals(type, "0")){
                // 如果type=0即查询全部
                cardList = this.cardMapper.selectByExample(example);
            }else{
                // 设置查询条件
                example.createCriteria().andEqualTo("type", type);
                cardList = this.cardMapper.selectByExample(example);
            }
        }else{
            // 对标题模糊查询
            example.createCriteria().andLike("title", "%" + title + "%");
            cardList = this.cardMapper.selectByExample(example);
        }
        // 对查询信息分装
        PageInfo<Card> pageInfo = new PageInfo<Card>(cardList);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList(),pageInfo.getPageNum());
    }
    
    /**
     * 插入一条记录 
     * @param card
     * @return
     */
    public boolean saveCard(Card card) {
        if(card == null){
            return false;
        }
        card.setCreated(new Date());
        card.setUpdated(card.getCreated());
        card.setStars(0);
        card.setUserId(1L);// TODO 从登录系统中获取，即从拦截器中保存的用户信息获取
        Integer count = this.cardMapper.insertSelective(card);
        return count.intValue() == 1;
    }

    /**
     * 根据id删除数据
     * @param cardId
     * @return
     */
    public boolean deleteCardById(Long cardId) {
        Integer count = this.cardMapper.deleteByPrimaryKey(cardId);
        return count == 1;
    }

    /**
     * 根据主键更新点赞数
     * @param card
     * @return
     */
    public boolean updateById(Long cardId, Integer stars) {
        Card card = new Card();
        card.setId(cardId);
        // 点赞数加一
        card.setStars(stars + 1);
        // 更新时间
        card.setUpdated(new Date());
        Integer count = this.cardMapper.updateByPrimaryKeySelective(card);
        return count == 1;
    }

    /**
     * 根据用户id查询card数据集合
     * @param userId
     * @param page
     * @param pRows
     * @return
     */
    public EasyUIResult queryCardListByUserId(Long userId, Integer page, Integer pRows) {
        Example example = new Example(Card.class);
        // 设置分页信息
        PageHelper.startPage(page, pRows);
        // 设置排序条件
        example.setOrderByClause("created DESC");
        example.createCriteria().andEqualTo("userId", userId);
        List<Card> cardList = this.cardMapper.selectByExample(example);
        // 对查询信息分装
        PageInfo<Card> pageInfo = new PageInfo<Card>(cardList);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList(),pageInfo.getPageNum());
    }
    /**
     * 接收前台传来的json字符串存储
     * @param json
     * @return
     */
    public boolean saveCard(String json) {
        try {
            if(StringUtils.isNotEmpty(json)){
                // 反序列化为Card对象
                Card card = MAPPER.readValue(json, Card.class);
                // 设置初始值
                card.setCreated(new Date());
                card.setUpdated(card.getCreated());
                card.setStars(0);
                Integer count = this.cardMapper.insert(card);
                return count.intValue() == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
