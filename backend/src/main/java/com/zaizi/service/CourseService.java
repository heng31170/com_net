package com.zaizi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaizi.mapper.CourseMapper;
import com.zaizi.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseService extends ServiceImpl<CourseMapper, Course> {
    @Autowired
    private CourseMapper courseMapper;
    //
    // 查询全部课程
    public List<Course> getAllCourses() {
        return list();
    }
    // 添加课程
    public void addCourse(Course course) {
        courseMapper.addCourse(course);
    }
    // 条件查询课程
    public List<Course> getCourses(String title,String category) {
        List<Course> courses = courseMapper.getCourses(title,category);
        return courses;
    }
    // 根据id获取课程
    public Course getCourseById(Integer courseId) {
        return this.getById(courseId);
    }
}
