package com.zaizi.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamAnswerWithUser {
    private Integer answerId;   // 答案ID
    private Integer examId;     // 考试ID
    private Integer userId;     // 用户ID
    private String content;      // 答案内容
    private LocalDateTime createdAt; // 创建时间
    private String username;     // 用户名
    private String name;         // 用户姓名
    private Integer score;
}