package com.wengxiaoxiong;

import com.wengxiaoxiong.model.User;
import com.wengxiaoxiong.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User u) {
        System.out.println(u.getName());
        u.setName("已经修改");
        return u;
    }
}
