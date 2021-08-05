package com.icampus.web.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.icampus.common.bean.EasyUIResult;
import com.icampus.web.bean.Content;
import com.icampus.web.bean.Message;
import com.icampus.web.service.IndexService;
import com.icampus.web.service.MessageService;
@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private IndexService indexService;
    @RequestMapping(value = "message", method = RequestMethod.GET)
    public ModelAndView toMessage(@RequestParam(value = "page", defaultValue = "1")Integer page){
        ModelAndView mv = new ModelAndView("message");
        EasyUIResult es = this.messageService.queryListByWhere(page, "stars");
        List<Content> statementList = this.indexService.queryContentList(3);
        if(es != null){
            // 精选简信
            mv.addObject("sMessageList", es.getRows());
        }
        EasyUIResult ec = this.messageService.queryListByWhere(page, "created");
        if(ec != null){
            // 最新简信
            mv.addObject("cMessageList", ec.getRows());
        }
        mv.addObject("statementList", statementList);
        return mv;
    }
    
    @RequestMapping(value = "message/save", method = RequestMethod.POST)
    public String save(Message message){
        this.messageService.save(message);
        System.out.println(message);
        return "redirect:/message.html";
    }
}
