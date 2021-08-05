package com.icampus.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.icampus.web.bean.Content;
import com.icampus.web.bean.Item;
import com.icampus.web.service.IndexService;
import com.icampus.web.service.SouvenirService;

@Controller
public class SouvenirController {
    @Autowired
    private SouvenirService souvenirService;
    @Autowired
    private IndexService indexService;
    @RequestMapping(value = "souvenir", method = RequestMethod.GET)
    public ModelAndView toSouvenir(){
        ModelAndView mv = new ModelAndView("souvenir");
        List<Item> itemList = this.souvenirService.queryList();
        List<Content> statementList = this.indexService.queryContentList(3);
        mv.addObject("itemList",itemList);
        mv.addObject("statementList",statementList);
        return mv;
    }
}
