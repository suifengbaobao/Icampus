package com.icampus.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.icampus.common.bean.EasyUIResult;
import com.icampus.manage.pojo.Message;
import com.icampus.manage.service.MessageService;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    private static final Integer S_ROWS = 12;
    private static final Integer C_ROWS = 8;
    private static final Integer P_ROWS = 9;
    /**
     * 根据条件查询
     * @param page
     * @param order
     * @return
     */
    @RequestMapping(value = "message", method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryMessageList(@RequestParam(value = "page",
                    defaultValue = "1") Integer page, @RequestParam(value = "order",
                    defaultValue = "stars")String order){
        EasyUIResult easyUIResult = null;
        try {
            if(order.equals("stars")){
                // 根据点赞数排序
                easyUIResult = this.messageService.queryListByStars(page,S_ROWS);
            }else if(order.equals("created")){
                // 根据创建时间查询
                easyUIResult = this.messageService.queryListByCreated(page,C_ROWS);
            }else{
                // 请求参数错误，响应400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            if(easyUIResult != null){
                // 查询成功，响应200
                return ResponseEntity.ok(easyUIResult);
            }else{
                // 查询成功，响应404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 服务器错误，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    /**
     * 查询卡片信息，根据用户Id
     * @param type
     * @return
     */
    @RequestMapping(value = "message/{userId}/{page}", method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryCardListByUserID(@PathVariable("userId")
                            Long userId, @PathVariable("page") Integer page){
        try {
         // 将请求参数转码
            EasyUIResult easyUIResult = this.messageService.queryMessageListByUserId(userId, page, P_ROWS);
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
     * 根据id更新点赞数，每次默认增加一
     * @param messageId
     * @return
     */
    @RequestMapping(value = "message/star/{messageId}", method = RequestMethod.GET)
    public ResponseEntity<Message> updateStarsById(@PathVariable("messageId") Long messageId){
        try {
            Message message = this.messageService.updateStarsById(messageId);
            if(message != null){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 保存一条简信
     * @param message
     * @return
     */
    @RequestMapping(value = "message/save", method = RequestMethod.POST)
    public ResponseEntity<Void> save(@RequestBody String json){
        try {
            boolean bool = this.messageService.save(json);
            if(bool){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
