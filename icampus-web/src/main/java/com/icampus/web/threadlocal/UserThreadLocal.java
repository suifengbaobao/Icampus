package com.icampus.web.threadlocal;

import com.icampus.web.bean.User;


public class UserThreadLocal {
    private static final ThreadLocal<User> LOCAL = new ThreadLocal<User> ();
    
    private UserThreadLocal(){}// 防止外部创建对象
    
    public static void set(User user){
        LOCAL.set(user);
    }
    
    public static User get(){
        return LOCAL.get();
    }
}
