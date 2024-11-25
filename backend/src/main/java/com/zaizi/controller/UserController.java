package com.zaizi.controller;


import com.zaizi.pojo.User;
import com.zaizi.service.UserService;
import com.zaizi.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    // 注册用户
    @PostMapping("/register")
    public void register(@RequestBody User user) {
        userService.register(user);
    }
    // 登录操作
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        User u = userService.login(user);
        if(u!=null) {
            Map<String,Object> claims = new HashMap<>();
            claims.put("userId",u.getUserId());
            claims.put("username",u.getUsername());
            String jwt = JwtUtils.generateJwt(claims);
            return ResponseEntity.ok(Map.of("status","success to login","user",u,"jwt",jwt));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败，用户名或密码错误");
    }
}