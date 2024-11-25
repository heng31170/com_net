package com.zaizi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaizi.mapper.UserMapper;
import com.zaizi.pojo.User;
import org.springframework.stereotype.Service;


@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public User login(User user) {
        return baseMapper.getUserByUserNameAndPasswd(user);
    }
    public void register(User user) {
        baseMapper.addUser(user);
    }
}
