package com.zaizi.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("course_comments")
public class CourseComment {
    @TableId
    private Integer commentId;
    private Integer courseId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
}