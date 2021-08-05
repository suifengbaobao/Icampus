package com.icampus.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icampus.manage.mapper.UserMapper;
import com.icampus.manage.pojo.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    public User queryUserById(Long userId) {
        return this.userMapper.selectByPrimaryKey(userId);
    }

}
