package com.icampus.web.service;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icampus.common.bean.EasyUIResult;
import com.icampus.common.httpclient.HttpResult;
import com.icampus.common.service.ApiService;
import com.icampus.web.bean.Message;
import com.icampus.web.bean.User;
import com.icampus.web.threadlocal.UserThreadLocal;

@Service
public class MessageService {
    @Autowired
    private ApiService apiService;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Value("${MANAGE_ICAMPUS_URL}")
    private String MANAGE_ICAMPUS_URL;
    /**
     * 根据条件查询简信集合
     * @param page
     * @param order
     * @return
     */
    public EasyUIResult queryListByWhere(Integer page, String order) {
        String url = MANAGE_ICAMPUS_URL + "/rest/api/message?page=" + page + "&order=" + order;
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isNotEmpty(jsonData)){
                return MAPPER.readValue(jsonData, EasyUIResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 保存一条简信
     * @param message
     */
    public Boolean save(Message message) {
        String url = MANAGE_ICAMPUS_URL + "/rest/api/message/save";
        try {
            // 添加用户id
            User user = UserThreadLocal.get();
            message.setUserId(user.getId());
            // 序列化order对象
            String json = MAPPER.writeValueAsString(message);
            // 做post请求
            HttpResult result = this.apiService.doPostJosn(url, json);
            if(result.getCode() == 201){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return false;
    }
}
