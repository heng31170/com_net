package com.zaizi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaizi.mapper.CommentMapper;
import com.zaizi.pojo.CommentWithUser;
import com.zaizi.pojo.CourseComment;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService extends ServiceImpl<CommentMapper, CourseComment> {
    // 根据课程id查询评论
//    public List<CourseComment> getCommentByCourseId(Integer courseId) {
//        return this.baseMapper.selectByCourseId(courseId);
//    }
    public List<CommentWithUser> getCommentByCourseId(Integer courseId) {
        return this.baseMapper.selectCommentsWithUserByCourseId(courseId);
    }
    // 添加评论
    public void addComment(CourseComment courseComment) {
        this.save(courseComment);
    }
    // 根据评论id删除评论
    public void delComment(Integer commentId) {
        this.baseMapper.deleteById(commentId);
    }
}
