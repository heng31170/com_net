package com.zaizi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zaizi.mapper.TeacherMapper;
import com.zaizi.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService extends ServiceImpl<TeacherMapper, Teacher> {
    @Autowired
    private TeacherMapper teacherMapper;
    // 获取全部教师数据
    public List<Teacher> getAllTeachers() {
        return list();
    }
    // 根据id获取教师
    public Teacher getTeacherById(Integer teacherId) {
        return getById(teacherId);
    }
    // 添加教师
    public void addTeacher(Teacher teacher) {
        teacherMapper.addTeacher(teacher);
    }
}
