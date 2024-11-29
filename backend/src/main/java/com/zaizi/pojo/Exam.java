package com.zaizi.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("exams")
public class Exam {
    @TableId
    private Integer examId;     // 考试ID
    private Integer userId;     // 创建考试的用户ID
    private String title;       // 考试标题
    private String text;        // 考试描述
    private LocalDateTime createdAt; // 创建时间
}