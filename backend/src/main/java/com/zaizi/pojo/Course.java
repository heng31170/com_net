package com.zaizi.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("courses")
public class Course {
    @TableId(value = "course_id")
    private Integer courseId;
    private String title;
    private String description;
    private Integer teacherId;
    private String syllabus;
    private String resources;
    private String category;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}