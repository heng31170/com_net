package com.zaizi.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamWithUser {
    private Integer examId;       // 考试ID
    private Integer userId;       // 创建考试的用户ID
    private String title;         // 考试标题
    private String text;          // 考试描述
    private LocalDateTime createdAt; // 创建时间
    private String username;      // 用户名
    private String name;          // 用户姓名
}
