package com.icampus.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icampus.manage.pojo.Article;
import com.icampus.manage.service.ArticleService;

@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    /**
     * 查询文章列表
     * @return
     */
    @RequestMapping(value = "article")
    public ResponseEntity<List<Article>> queryArticleList(){
        try {
            List<Article> articleList = this.articleService.queryArticleList();
            if(articleList != null){
                // 查询成功，响应200
                return ResponseEntity.ok(articleList);
            }else{
                // 查询失败，响应404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 服务器内部错误，响应505
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        
    }
}
