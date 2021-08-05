package com.icampus.web.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icampus.common.service.ApiService;
import com.icampus.web.bean.Item;

@Service
public class SouvenirService {
    @Autowired
    private ApiService apiService;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Value("${MANAGE_ICAMPUS_URL}")
    private String MANAGE_ICAMPUS_URL;
    /**
     * 从后台获取item集合数据
     * @return
     */
    public List<Item> queryList() {
        String url = MANAGE_ICAMPUS_URL + "/rest/api/item";
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isNotEmpty(jsonData)){
                // 构造集合类型
                return MAPPER.readValue(jsonData, MAPPER.getTypeFactory().constructCollectionType(List.class, Item.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
