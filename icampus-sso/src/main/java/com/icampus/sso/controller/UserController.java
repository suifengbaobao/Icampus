package com.icampus.sso.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icampus.common.utils.CookieUtils;
import com.icampus.sso.pojo.User;
import com.icampus.sso.service.UserService;


@Controller
@RequestMapping("user")
public class UserController {
    private static final String COOKIE_NAME = "ICAMPUS_TOKEN";
    @Autowired
    private UserService userService;
    
    /**
     * 用户注册页面
     * 
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    /**
     * 用户登录界面
     * 
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    
    /**
     * 验证用户注册信息
     * 
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value = "/{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param") String param,
            @PathVariable("type") Integer type) {
        try {
            Boolean bool = this.userService.check(param, type);
            if (bool == null) {
                // 参数错误, 响应406
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 服务器错误，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    /**
     * 验证注册
     * @param user
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "doRegister", method = RequestMethod.POST)
    public ResponseEntity<Void> doRegister(User user) {
        try {
            Boolean bool = this.userService.doRegister(user);
            if(bool){
                return ResponseEntity.ok(null);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
//    /**
//     * 根据用户id查询
//     * @param userId
//     * @return
//     */
//    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
//    public ResponseEntity<User> queryUserById(@PathVariable("userId") Long userId){
//        try {
//            User user = this.userService.queryUserById(userId);
//            if(user != null){
//                // 查询成功，响应200
//                return ResponseEntity.ok(user);
//            }else{
//                // 查询失败，响应404
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 内部服务器错误，响应500
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//    }
    
    /**
     * 用户登录验证
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String token = this.userService.doLogin(user.getUsername(), user.getPassword());
            if (StringUtils.isEmpty(token)) {
                // 登录失败
                result.put("status", 500);
                return result;
            }
            // 将token写入到cookie中
            CookieUtils.setCookie(request, response, COOKIE_NAME, token);
            // 登录成功
            result.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", 500);
        }
        return result;
    }
    
    

    /**
     * 根据token查询用户
     * 
     * @param token
     * @return
     */
    @RequestMapping(value = "{token}", method = RequestMethod.GET)
    public ResponseEntity<User> queryUserByToken(@PathVariable("token") String token) {
        try {
            User user = this.userService.queryUserByToken(token);
            if (null == user) {
                // 登录失败
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "logout/{token}", method = RequestMethod.GET)
    public void logout(@PathVariable("token") String token, HttpServletRequest request,
            HttpServletResponse response) {// TODO Value注解失效
        // 删除cookie
        CookieUtils.deleteCookie(request, response, COOKIE_NAME);
        // 删除redis中的数据
        Boolean bool = this.userService.logout(token);
        if (bool) {
            // 退出成功
            try {
                response.sendRedirect("http://www.icampus.com");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
