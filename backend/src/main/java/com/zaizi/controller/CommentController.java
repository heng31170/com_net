package com.zaizi.controller;


import com.zaizi.pojo.CourseComment;
import com.zaizi.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    // 根据课程id获取评论
    @GetMapping("/comment/{courseId}")
    public List<CourseComment> getComments(@PathVariable Integer courseId) {
        return commentService.getCommentByCourseId(courseId);
    }
    // 添加评论
    @PostMapping("/comment/add")
    public void addComment(@RequestBody CourseComment courseComment) {
        commentService.addComment(courseComment);
    }

}
