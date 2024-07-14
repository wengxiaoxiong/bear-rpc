package com.wengxiaoxiong;

import com.wengxiaoxiong.model.User;
import com.wengxiaoxiong.service.UserService;

public class ConsumeExample {
    public static void main(String[] args) {
        UserService userService = null;
        User user = new User();
        user.setName("test");
        System.err.println(userService.getUser(user));
        
    }
}
