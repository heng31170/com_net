package com.zaizi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zaizi.pojo.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    // 添加课程
    @Insert("insert into courses (title,description,category,avatar) values (#{title},#{description},#{category},#{avatar})")
    @Options(useGeneratedKeys = true,keyProperty = "courseId")
    void addCourse(Course course);
    // 条件查询课程
    List<Course> getCourses(@Param("title") String title,@Param("category") String category);
}
