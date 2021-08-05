package com.icampus.web.interceptors;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.icampus.common.utils.CookieUtils;
import com.icampus.web.bean.User;
import com.icampus.web.service.UserService;
import com.icampus.web.threadlocal.UserThreadLocal;

/**
 * 用户权限拦截器，当进入订单请求时判断是否登录
 * @author suife
 *
 */
public class UserHandlerInterceptor implements HandlerInterceptor{
    public static final String COOKIE_NAME = "ICAMPUS_TOKEN";
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //tomcat中使用的线程池管理，一个线程可能多次使用
        // 获取cookie中的值
        String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
//        @SuppressWarnings("resource")
//        int itemId = new Scanner(request.getServletPath()).nextInt();// TODO 请求登陆后直接转到订单页面
        String loginUrl = this.userService.ICAMPUS_SSO_URL + "/user/login.html";
        if(StringUtils.isEmpty(token)){
            // 未登录，返回登录页面
            response.sendRedirect(loginUrl);
            return false;
        }
        User user = this.userService.queryByToken(token);
        if(null == user){
            // 登录超时，返回登录页面
            response.sendRedirect(loginUrl);
            return false;
        }
        // 用户已经登录
        
        UserThreadLocal.set(user);// 向本地线程中添加user对象
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        UserThreadLocal.set(null);// 清空本地线程中的user对象，防止引用错乱
    }

}
