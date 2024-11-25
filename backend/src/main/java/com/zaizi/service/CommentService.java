package com.zaizi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaizi.mapper.CommentMapper;
import com.zaizi.pojo.CourseComment;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService extends ServiceImpl<CommentMapper, CourseComment> {
    // 根据课程id查询评论
    public List<CourseComment> getCommentByCourseId(Integer courseId) {
        return this.baseMapper.selectByCourseId(courseId);
    }
    // 添加评论
    public void addComment(CourseComment courseComment) {
        this.save(courseComment);
    }
}
