package com.icampus.web.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icampus.common.service.ApiService;
import com.icampus.web.bean.Article;
import com.icampus.web.bean.Content;

@Service
public class IndexService {
    @Autowired
    private ApiService apiService;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Value("${MANAGE_ICAMPUS_URL}")
    private String MANAGE_ICAMPUS_URL;
    /**
     * 查询内容集合信息
     * @param i
     * @return
     */
    public List<Content> queryContentList(int cid) {
        String url = MANAGE_ICAMPUS_URL + "/rest/api/content/" + cid;
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isNotEmpty(jsonData)){
                return MAPPER.readValue(jsonData, MAPPER.getTypeFactory().constructCollectionType(List.class, Content.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查询文章列表
     * @return
     */
    public List<Article> queryArticleList(){
        String url = MANAGE_ICAMPUS_URL + "/rest/api/article/";
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isNotEmpty(jsonData)){
                return MAPPER.readValue(jsonData, MAPPER.getTypeFactory().constructCollectionType(List.class, Article.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
