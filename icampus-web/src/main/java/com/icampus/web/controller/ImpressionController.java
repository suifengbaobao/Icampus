package com.icampus.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.icampus.common.bean.EasyUIResult;
import com.icampus.web.bean.Card;
import com.icampus.web.bean.Content;
import com.icampus.web.service.ImpressionService;
import com.icampus.web.service.IndexService;

@Controller
public class ImpressionController {
    @Autowired
    private ImpressionService impressionService;
    @Autowired
    private IndexService indexService;
    private static final Integer ROWS = 16;
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "impression", method = RequestMethod.GET)
    public ModelAndView toImpression(@RequestParam(value = "page", defaultValue = "1")
                        Integer page,@RequestParam(value = "type", defaultValue = "0")
                        String type, @RequestParam(value = "title", defaultValue = "") String title){
        try {
         // 将请求参数转码
            title = new String(title.getBytes("iso8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("impression");
        EasyUIResult easyUIResult = this.impressionService.queryCardList(type, title, page);
        List<Content> statementList = this.indexService.queryContentList(3);
        List<Content> activityList = this.indexService.queryContentList(4);
        List<Card> cardList = null;
        Long pages = 1L;
        Integer currentPage = 1;
        Long total = 0L;
        if(easyUIResult != null){
            total = easyUIResult.getTotal();
            pages = total % ROWS == 0 ? total / ROWS : total / ROWS + 1;
            cardList = (List<Card>) easyUIResult.getRows();
            currentPage = easyUIResult.getCurrentPage();
        }
        mv.addObject("cardList", cardList);
        mv.addObject("pages", pages);
        mv.addObject("currentPage", currentPage);
        mv.addObject("statementList", statementList);
        mv.addObject("activityList", activityList);
        return mv;
    }
}
