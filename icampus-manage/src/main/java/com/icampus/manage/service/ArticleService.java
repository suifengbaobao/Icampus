package com.icampus.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icampus.common.bean.EasyUIResult;
import com.icampus.manage.mapper.ArticleMapper;
import com.icampus.manage.pojo.Article;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articelMapper;
    private static final Integer PAGE = 1;// 查询第一页
    private static final Integer ROWS = 4;// 每页显示四条记录
    /**
     * 查询文章列表
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Article> queryArticleList() {
        return (List<Article>) queryArticleList(PAGE, ROWS).getRows();
    }
    
    /**
     * 根据条件查询，并分页
     * @param page
     * @param rows
     * @return
     */
    public EasyUIResult queryArticleList(Integer page, Integer rows){
        Example example = new Example(Article.class);
        // 设置分页信息
        PageHelper.startPage(page, rows);
        // 设置排序条件
        example.setOrderByClause("created DESC");
        List<Article> articleList = this.articelMapper.selectByExample(example);
        // 分装分页信息
        PageInfo<Article> pageInfo = new PageInfo<Article>(articleList);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPageNum());
    }

}
