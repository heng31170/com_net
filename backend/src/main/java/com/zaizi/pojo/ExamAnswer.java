package com.zaizi.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExamAnswer {
    private Integer answerId;
    private Integer examId;
    private Integer studentId;
    private String questionText;
    private String studentAnswer;
    private LocalDateTime submittedAt;
}