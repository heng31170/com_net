package com.zaizi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zaizi.pojo.CommentWithUser;
import com.zaizi.pojo.CourseComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CommentMapper extends BaseMapper<CourseComment> {
    // 根据课程id获取课程评论
//    @Select("SELECT * FROM course_comments WHERE course_id = #{courseId}")
//    List<CourseComment> selectByCourseId(Integer courseId);
    // 根据课程id获取课程评论与用户
    @Select("SELECT cc.comment_id, cc.course_id, cc.user_id, cc.content, cc.created_at, u.username, u.name " +
            "FROM course_comments cc " +
            "JOIN users u ON cc.user_id = u.user_id " +
            "WHERE cc.course_id = #{courseId}")
    List<CommentWithUser> selectCommentsWithUserByCourseId(Integer courseId);
}
