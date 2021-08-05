package com.icampus.manage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.icampus.manage.pojo.User;
import com.icampus.manage.service.UserService;

@Controller
@RequestMapping("api/user")
public class ApiUserController {
    @Autowired
    private UserService userService;
    /**
     * 根据用户id查询
     * @param userId
     * @return
     */
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> queryUserById(@PathVariable("userId") Long userId){
        try {
            User user = this.userService.queryUserById(userId);
            if(user != null){
                // 查询成功，响应200
                return ResponseEntity.ok(user);
            }else{
                // 查询失败，响应404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 内部服务器错误，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
