package com.zaizi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zaizi.pojo.CourseComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CommentMapper extends BaseMapper<CourseComment> {
    // 根据课程id获取课程评论
    @Select("SELECT * FROM course_comments WHERE course_id = #{courseId}")
    List<CourseComment> selectByCourseId(Integer courseId);
}
