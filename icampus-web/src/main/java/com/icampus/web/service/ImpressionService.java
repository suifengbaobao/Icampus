package com.icampus.web.service;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icampus.common.bean.EasyUIResult;
import com.icampus.common.httpclient.HttpResult;
import com.icampus.common.service.ApiService;
import com.icampus.web.bean.Card;
import com.icampus.web.threadlocal.UserThreadLocal;
@Service
public class ImpressionService {
    @Autowired
    private ApiService apiService;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Value("${MANAGE_ICAMPUS_URL}")
    private String MANAGE_ICAMPUS_URL;
    /**
     * 查询card信息
     * @param type
     * @param title
     * @param page
     * @return
     */
    public EasyUIResult queryCardList(String type, String title, Integer page) {
        // 后台请求地址
        String url = MANAGE_ICAMPUS_URL + "/rest/api/card?type=" + type + "&title=" + title + "&page=" + page;
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isNotEmpty(jsonData)){
                // 反序列化JSONData对象
                return MAPPER.readValue(jsonData, EasyUIResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 查询card信息,通过用户ID
     * @param type
     * @param title
     * @param page
     * @return
     */
    public EasyUIResult queryCardListByUserID(Integer page) {
        // 后台请求地址
        Long userId = UserThreadLocal.get().getId();
        String url = MANAGE_ICAMPUS_URL + "/rest/api/card/" + userId + "/" + page;
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isNotEmpty(jsonData)){
                // 反序列化JSONData对象
                return MAPPER.readValue(jsonData, EasyUIResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增一张卡片记录
     * @param card
     * @return
     */
    public boolean save(Card card) {
        try {
            Long userId = UserThreadLocal.get().getId();
            card.setUserId(userId);
            String url = MANAGE_ICAMPUS_URL + "/rest/api/card/save";
            HttpResult httpResult = this.apiService.doPostJosn(url, MAPPER.writeValueAsString(card));
            if(httpResult.getCode() == 201){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
