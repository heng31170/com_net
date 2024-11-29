package com.zaizi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zaizi.pojo.Exam;
import com.zaizi.pojo.ExamWithUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExamMapper extends BaseMapper<Exam> {
    // 获取考试及用户
    @Select("SELECT e.exam_id, e.user_id, e.title, e.text, e.created_at, u.username, u.name " +
            "FROM exams e " +
            "JOIN users u ON e.user_id = u.user_id")
    List<ExamWithUser> getAllExamsWithUsers();
}
