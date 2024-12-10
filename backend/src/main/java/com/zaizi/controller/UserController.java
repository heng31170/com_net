package com.zaizi.controller;


import com.zaizi.pojo.User;
import com.zaizi.service.UserService;
import com.zaizi.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败，用户名或密码或身份错误");
    }
    // 根据id获取用户
    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }
    // 修改密码
    @PostMapping("/passwd/update")
    public ResponseEntity<String> updatePasswd(@RequestBody User user) {
            int res = userService.updatePasswd(user);
            if(res == 1) {
                return ResponseEntity.ok("密码更新成功");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("密码更新失败: ");
    }
}
