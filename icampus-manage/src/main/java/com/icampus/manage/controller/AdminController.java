package com.icampus.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.icampus.common.bean.EasyUIResult;
import com.icampus.manage.pojo.Content;
import com.icampus.manage.service.ContentService;
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private ContentService contentService;
    /**
     * 去主页
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "query", method = RequestMethod.GET)
    public ModelAndView toList(Content content, @RequestParam(value = "page", defaultValue = "1")
                            Integer page){
        ModelAndView mv = new ModelAndView("admin");
        EasyUIResult easyUIResult  = this.contentService.queryContentList(content,page);
        List<Content> contentList = null;
        Long pages = 1L;
        Integer currentPage = 1;
        Long total = 0L;
        if(easyUIResult != null){
            total = easyUIResult.getTotal();
            pages = total % 12 == 0 ? total / 12 : total / 12 + 1;
            contentList = (List<Content>) easyUIResult.getRows();
            currentPage = easyUIResult.getCurrentPage();
        }
        mv.addObject("contentList", contentList);
        mv.addObject("pages", pages);
        mv.addObject("currentPage", currentPage);
        return mv;
    }
}