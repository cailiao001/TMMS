package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pojo.User;


public interface UserService extends IService<User> {
    void register(User user);

    User login(User user);

}
