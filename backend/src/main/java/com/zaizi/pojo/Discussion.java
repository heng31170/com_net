package com.zaizi.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("discussions")
public class Discussion {
    @TableId
    private Integer discussionId;
    private Integer questionId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
}