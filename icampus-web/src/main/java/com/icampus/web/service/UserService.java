package com.icampus.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icampus.common.service.ApiService;
import com.icampus.web.bean.User;

@Service
public class UserService {
    @Autowired
    private ApiService apiService;
    @Value("${ICAMPUS_SSO_URL}")
    public String ICAMPUS_SSO_URL;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 根据token查询用户信息
     * @param  token
     * @return 返回用户对象
     */
    public User queryByToken(String token){
        String url = ICAMPUS_SSO_URL + "/service/user/" + token;
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isNotEmpty(jsonData)){
                return MAPPER.readValue(jsonData, User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }
}
