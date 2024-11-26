package com.zaizi.pojo;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CommentWithUser {private Integer commentId;
    private Integer courseId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
    private String username;  // 用户名
    private String name;      // 用户的真实姓名
}
