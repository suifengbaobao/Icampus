package com.icampus.manage.controller;

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
public class ContentController {
    @Autowired
    private ContentService contentService;
    /**
     * 根据内容类型
     * @param cid
     * @return
     */
    @RequestMapping(value = "content/{cid}", method = RequestMethod.POST)
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
