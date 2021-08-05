package com.icampus.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icampus.common.service.RedisService;
import com.icampus.sso.mapper.UserMapper;
import com.icampus.sso.pojo.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Integer REDIS_TIME = 60 * 30;
    
    /**
     * 检验用户是否存在
     * @param param
     * @param type
     * @return
     */
    public Boolean check(String param, Integer type) {
        User record = new User();
        // 根据传入的类型，设置查询条件
        switch(type){
            case 1:
                record.setUsername(param);
                break;
            case 2:
                record.setEmail(param);
                break;
            default:
                return null;
        }
        return this.userMapper.selectOne(record) == null;
    }
    
    /**
     * 注册用户
     * @param user
     * @return
     */
    public Boolean doRegister(User user) {
        // 参数初始化
        user.setId(null);
        user.setType("普通会员");
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        // 密码md5加密
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        Integer count = this.userMapper.insert(user);
        return count.intValue() == 1;
    }
    
    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    public User queryUserById(Long userId) {
        return this.userMapper.selectByPrimaryKey(userId);
    }
    
    
    /**
     * 用户登录
     * @param user
     * @return
     * @throws Exception 
     */
    public String doLogin(String username, String password) throws Exception {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        if(null == user){
            // 没有该用户
            return null;
        }
        if(! StringUtils.equals(DigestUtils.md5Hex(password), user.getPassword())){// 传入的密码要转换为md5格式
            // 密码不正确
            return null;
        }
        // 登录成功
        // 根据用户名和当前时间生成特定的token
        String token = DigestUtils.md5Hex(username + user.getClass().toString());
        // 这里把redis当做内存数据使用，而不是数据缓存，所以逻辑是和业务逻辑一起的，抛出异常
        this.redisService.set("TOKEN_" + token, REDIS_TIME, MAPPER.writeValueAsString(user));
        return token;
    }

    public User queryUserByToken(String token) {
        String jsonData = this.redisService.get("TOKEN_" + token);
        if(StringUtils.isEmpty(jsonData)){
            // 没有登录过
            return null;
        }
        // 更新redis中token的生存时间(非常重要，调用这个方法时意味着用户一直在浏览网页)
        this.redisService.expire("TOKEN_" + token, REDIS_TIME);
        // 查询成功
        try {
            User user = MAPPER.readValue(jsonData, User.class);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean logout(String token) {
        // 删除redis中的数据
        Long count = this.redisService.delete("TOKEN_" + token);
        return count == 1;
    }
}
