package com.icampus.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.icampus.common.bean.EasyUIResult;
import com.icampus.web.bean.Card;
import com.icampus.web.bean.Content;
import com.icampus.web.bean.User;
import com.icampus.web.service.ImpressionService;
import com.icampus.web.service.IndexService;
import com.icampus.web.service.UserService;
import com.icampus.web.threadlocal.UserThreadLocal;

@Controller
public class PersonController {
    private static final Integer ROWS = 9;
    @Autowired
    private ImpressionService impressionService;
    @Autowired
    private UserService userService;
    @Autowired
    private IndexService indexService;
    /**
     * 
     * @param page
     * @return
     */
    @RequestMapping(value = "person", method = RequestMethod.GET)
    public ModelAndView toPerson(@RequestParam(value = "page", defaultValue = "1")
                                  Integer page){
        ModelAndView mv = new ModelAndView("person");
        List<Content> statementList = this.indexService.queryContentList(3);
        User user = UserThreadLocal.get();
        mv.addObject("statementList", statementList);
        mv.addObject("user", user);
        return mv;
    }
    /**
     * person-index即主页展示
     * @param page
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "person-index", method = RequestMethod.GET)
    public ModelAndView toPersonIndex(@RequestParam(value = "page", defaultValue = "1")Integer page){
        ModelAndView mv = new ModelAndView("person-index");
        EasyUIResult easyUIResult = this.impressionService.queryCardListByUserID(page);
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
        User user = UserThreadLocal.get();
        mv.addObject("cardList", cardList);
        mv.addObject("pages", pages);
        mv.addObject("currentPage", currentPage);
        mv.addObject("user", user);
        return mv;
    }
    
    /**
     * person-index即发送图片
     * @return
     */
    @RequestMapping(value = "person-send", method = RequestMethod.GET)
    public String toPersonSend(){
        return "person-send";
    }
    /**
     * 存储一条card记录
     * @param card
     * @return
     */
    @RequestMapping(value = "/person/card/save", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveCard(Card card){
        try {
            boolean bool = this.impressionService.save(card);
            if(bool){
                // 新增成功，响应201
                return ResponseEntity.status(HttpStatus.CREATED).body(bool);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bool);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
