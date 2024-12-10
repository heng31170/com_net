package com.zaizi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaizi.mapper.UserMapper;
import com.zaizi.pojo.User;
import org.springframework.stereotype.Service;


@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    // 登录
    public User login(User user) {
        return baseMapper.getUserByUserNameAndPasswd(user);
    }
    // 注册
    public void register(User user) {
        baseMapper.addUser(user);
    }
    // 根据id获取用户信息
    public User getUserById(Integer userId) {
        return getById(userId);
    }
    // 修改密码
    public int updatePasswd(User user) {
        return baseMapper.updatePasswd(user);
    };
}
