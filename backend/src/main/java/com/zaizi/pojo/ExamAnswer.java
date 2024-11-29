package com.zaizi.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("exam_answers")
public class ExamAnswer {
    @TableId
    private Integer answerId;   // 答案ID
    private Integer examId;     // 指向考试表的考试ID
    private Integer userId;     // 答题的用户ID
    private String content;      // 答案内容
    private LocalDateTime createdAt; // 创建时间
    private Integer score;
}