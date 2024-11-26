package com.zaizi.pojo;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class DiscussionWithUser {
    private Integer discussionId;
    private Integer questionId;
    private Integer userId;
    private String content;
    private LocalDateTime createdAt;
    private String username;
    private String name;
}
