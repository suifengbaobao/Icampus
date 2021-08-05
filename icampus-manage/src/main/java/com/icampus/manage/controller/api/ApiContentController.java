package com.icampus.manage.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.icampus.manage.pojo.Content;
import com.icampus.manage.service.ContentService;

@Controller
@RequestMapping("api")
public class ApiContentController {
    @Autowired
    private ContentService contentService;
    /**
     * 根据内容类型
     * @param cid
     * @return
     */
    @RequestMapping(value = "content/{cid}", method = RequestMethod.GET)
    public ResponseEntity<List<Content>> queryContentList(@PathVariable("cid") Integer cid){
        try {
            List<Content> contentList = this.contentService.queryContentList(cid);
            if(contentList != null){
                return ResponseEntity.ok(contentList);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
