package com.icampus.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.icampus.common.bean.EasyUIResult;
import com.icampus.manage.mapper.ContentMapper;
import com.icampus.manage.pojo.Content;

@Service
public class ContentService {
    @Autowired
    private ContentMapper contentMapper;
    private static final Integer INDEX_AD_ROWS = 5;
    private static final Integer VIDEO_ROWS = 4;
    private static final Integer IMPRESSION_AD_ROWS = 1;
    private static final Integer STATEMENT_ROWS = 1;
    private static final Integer PAGE = 1;// 始终默认为1页；
    /**
     * 根据内容类型查询
     * @param cid
     * @return
     */
    public List<Content> queryContentList(Integer cid) {
        // 根据不同的cid设置分页条件
        if(cid == 1){
            PageHelper.startPage(PAGE, INDEX_AD_ROWS);  
        }else if(cid == 2){
            PageHelper.startPage(PAGE, VIDEO_ROWS); 
        }else if(cid == 3){
            PageHelper.startPage(PAGE, IMPRESSION_AD_ROWS);
        }else if(cid == 4){
            PageHelper.startPage(PAGE, STATEMENT_ROWS);
        }else{
            return null;
        }
        Example example = new Example(Content.class);
        // 设置查询条件
        example.createCriteria().andEqualTo("cid", cid);
        // 设置排序条件
        example.setOrderByClause("created DESC");
        return this.contentMapper.selectByExample(example);
    }
    /**
     * 根据条件查询
     * @return
     */
    public EasyUIResult queryContentList(Content content, Integer page2) {
        PageHelper.startPage(page2, 12);
        List<Content> contentList = this.contentMapper.select(content);
        PageInfo<Content> pageInfo = new PageInfo<Content>(contentList);
        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList(),pageInfo.getPageNum());
    }
    
}
