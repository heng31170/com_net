package com.zaizi.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("discussion_questions")
public class DiscussionQuestion {
    @TableId
    private Integer questionId;
    private Integer courseId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
}