package com.icampus.manage.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icampus.common.bean.EasyUIResult;
import com.icampus.manage.mapper.MessageMapper;
import com.icampus.manage.pojo.Card;
import com.icampus.manage.pojo.Message;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 根据点赞数排序
     * @param page
     * @param sRows
     * @return
     */
    public EasyUIResult queryListByStars(Integer page, Integer sRows) {
        Example example = new Example(Message.class);
        // 设置排序规则为按点赞数倒序
        example.setOrderByClause("stars DESC");
        // 设置分页信息
        PageHelper.startPage(page, sRows);
        List<Message> messageList = this.messageMapper.selectByExample(example);
        // 分装分页信息
        PageInfo<Message> pageInfo = new PageInfo<Message>(messageList);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum());
    }
    /**
     * 根据创建时间排序
     * @param page
     * @param cRows
     * @return
     */
    public EasyUIResult queryListByCreated(Integer page, Integer cRows) {
        Example example = new Example(Message.class);
        // 设置排序规则为按点赞数倒序
        example.setOrderByClause("created DESC");
        // 设置分页信息
        PageHelper.startPage(page, cRows);
        List<Message> messageList = this.messageMapper.selectByExample(example);
        // 分装分页信息
        PageInfo<Message> pageInfo = new PageInfo<Message>(messageList);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum());
    }
    
    /**
     * 根id查询简信信息
     * @param messageId
     * @return
     */
    public Message queryById(Long messageId){
        return this.messageMapper.selectByPrimaryKey(messageId);
    }
    /**
     * 根据id更新点赞数
     * @param messageId
     * @return
     */
    public Message updateStarsById(Long messageId) {
        Message message = queryById(messageId);
        if(message == null){
            return null;
        }
        message.setUpdated(new Date());
        message.setStars(message.getStars() + 1);// 每次默认加一个赞
        Integer count = this.messageMapper.updateByPrimaryKey(message);
        if(count.intValue() == 1){
            return message;
        }
        return null;
    }
    /**
     * 保存一条简信
     * @param message
     * @return
     */
    public boolean save(String json) {
        Integer count = 0;
        try {
            // 反序列化获取Message对象
            Message message = MAPPER.readValue(json, Message.class);
            message.setCreated(new Date());
            message.setUpdated(message.getCreated());
            count = this.messageMapper.insertSelective(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count.intValue() == 1;
    }
    /**
     * 根据用户id查询message数据集合
     * @param userId
     * @param page
     * @param pRows
     * @return
     */
    public EasyUIResult queryMessageListByUserId(Long userId, Integer page, Integer pRows) {
        Example example = new Example(Card.class);
        // 设置分页信息
        PageHelper.startPage(page, pRows);
        // 设置排序条件
        example.setOrderByClause("created DESC");
        example.createCriteria().andEqualTo("userId", userId);
        List<Message> cardList = this.messageMapper.selectByExample(example);
        // 对查询信息分装
        PageInfo<Message> pageInfo = new PageInfo<Message>(cardList);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList(),pageInfo.getPageNum());
    }

}
