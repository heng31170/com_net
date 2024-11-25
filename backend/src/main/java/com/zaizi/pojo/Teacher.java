package com.zaizi.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("teachers")
public class Teacher {
    @TableId
    private Integer teacherId;
    private Integer userId;
    private String name;
    private String bio;
    private String avatar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}