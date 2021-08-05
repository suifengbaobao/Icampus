package com.icampus.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.icampus.web.bean.Article;
import com.icampus.web.bean.Content;
import com.icampus.web.service.IndexService;

@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;
    /**
     * 处理首页请求
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView toIndex(){
        ModelAndView mv = new ModelAndView("index");
        // cid=1 表示为首页广告
        List<Content> sAdList = this.indexService.queryContentList(1);
        List<Content> videoList = this.indexService.queryContentList(2);
        List<Content> statementList = this.indexService.queryContentList(3);
        List<Article> articleList = this.indexService.queryArticleList();
        mv.addObject("sAdList", sAdList);
        mv.addObject("videoList", videoList);
        mv.addObject("statementList", statementList);
        mv.addObject("articleList", articleList);
        return mv;
    }
}
