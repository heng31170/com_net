package com.zaizi.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    @TableId
    private Integer userId;
    private String username;
    private String passwd; // 修改为 passwd
    private String oldPasswd;
    private String name;
    private String role; // 'teacher' 或 'student'
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}