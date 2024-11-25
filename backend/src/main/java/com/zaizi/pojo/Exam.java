package com.zaizi.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Exam {
    private Integer examId;
    private Integer courseId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}