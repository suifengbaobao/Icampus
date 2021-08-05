package com.icampus.manage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.icampus.common.bean.EasyUIResult;
import com.icampus.manage.pojo.Card;
import com.icampus.manage.service.CardService;

@Controller
public class CardController {
    @Autowired
    private CardService cardService;
    // 每页查询的总条数
    private static final Integer ROWS = 16;
    private static final Integer P_ROWS = 9;
    /**
     * 查询卡片信息，根据类型查询
     * @param type
     * @return
     */
    @RequestMapping(value = "card", method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryCardListByType(@RequestParam(value = "page", defaultValue = "1")
                                    Integer page,@RequestParam(value = "type", defaultValue = "0")
                                    String type, @RequestParam(value = "title", defaultValue = "") String title){
        try {
         // 将请求参数转码
            title = new String(title.getBytes("iso8859-1"), "utf-8");
            EasyUIResult easyUIResult = this.cardService.queryCardListByType(type, title, page, ROWS);
            if(easyUIResult != null){
                // 查询成功，响应200
                return ResponseEntity.ok(easyUIResult);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
   
    /**
     * 查询卡片信息，根据用户Id
     * @param type
     * @return
     */
    @RequestMapping(value = "card/{userId}/{page}", method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryCardListByUserID(@PathVariable("userId")
                            Long userId, @PathVariable("page") Integer page){
        try {
         // 将请求参数转码
            EasyUIResult easyUIResult = this.cardService.queryCardListByUserId(userId, page, P_ROWS);
            if(easyUIResult != null){
                // 查询成功，响应200
                return ResponseEntity.ok(easyUIResult);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 添加一条card记录
     * @param card
     * @return
     */
    @RequestMapping(value = "card/save", method = RequestMethod.POST)
    public ResponseEntity<Void> saveCard(Card card){
        try {
            boolean bool = this.cardService.saveCard(card);
            if(bool){
                // 插入数据成功，返回201
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }else{
                // 插入数据失败，返回500
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    /**
     * 根据id删除card数据
     * @param cardId
     * @return
     */
    @RequestMapping(value = "card/{cardId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCardById(@PathVariable("cardId") Long cardId){
        try {
            boolean bool = this.cardService.deleteCardById(cardId);
            if(bool){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
