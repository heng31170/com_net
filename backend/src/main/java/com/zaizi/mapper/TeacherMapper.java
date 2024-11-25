package com.zaizi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zaizi.pojo.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    // 添加教师
    @Insert("insert into teachers (name,bio,avatar) values (#{name},#{bio},#{avatar})")
    @Options(useGeneratedKeys = true,keyProperty = "teacherId")
    void addTeacher(Teacher teacher);
}
